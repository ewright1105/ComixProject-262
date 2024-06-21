package model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class XMLAdapter implements DatabaseAdapter{
    public int export_index = 1;
    @Override
    public File export(Collection<String[]> data) {
        String filepath = "data/exported/XMLexport-" + export_index + ".xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

            for (String[] array : data) {
                Element objectElement = doc.createElement("object");
                for (String value : array) {
                    Element valueElement = doc.createElement("values");
                    valueElement.appendChild(doc.createTextNode(value));
                    objectElement.appendChild(valueElement);
                }
                rootElement.appendChild(objectElement);
            }

            File file = new File(filepath);
            FileWriter writer = new FileWriter(file);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            writer.close();

            export_index++;
            System.out.println(filepath);
            return file;
            
        } catch (Exception e) {
            System.err.println("Error writing XML to file: " + e.getMessage());
            return null;
        }
    }


    @Override
    public Collection<String[]> importFile(File file) {
        Collection<String[]> data = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList objectNodes = doc.getElementsByTagName("object");
            for (int i = 0; i < objectNodes.getLength(); i++) {
                Element objectElement = (Element) objectNodes.item(i);
                NodeList valueNodes = objectElement.getElementsByTagName("values");
                String[] array = new String[valueNodes.getLength()];
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    Element valueElement = (Element) valueNodes.item(j);
                    array[j] = valueElement.getTextContent();
                }
                data.add(array);
            }
        } catch (Exception e) {
            System.err.println("Error reading XML from file: " + e.getMessage());
        }
        return data;
    }

    // public static void main(String[] args) {
    //     XMLAdapter xmlAdapter = new XMLAdapter();
    //     Collection<String[]> data = new ArrayList<>();
    //     data.add(new String[]{"Absolute Carnage Vs Deadpool","2A","","Regular Tyler Kirkham Cover","Marvel Comics","Sep 11, 2019","Comic","Feb 13, 2020","Frank Tieri | Marcelo Ferreira | Andy Owens"});
    //     data.add(new String[]{"Absolute Carnage Vs Deadpool","2B","","Variant Ed McGuinness Cover","Marvel Comics","Sep 11, 2019","Comic","Feb 13, 2020","Frank Tieri | Marcelo Ferreira | Andy Owens"});

    //     File file = xmlAdapter.export(data);
    //     Collection<String[]> importedData = xmlAdapter.importFile(file);
    //     for (String[] comic : importedData) {
    //         for (String field : comic) {
    //             System.out.print(field + " ");
    //         }
    //         System.out.println();
    //     }
    //     System.out.println(file);
    // }

   
}
