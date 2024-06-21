package model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CSVAdapter implements DatabaseAdapter {
    public int export_index = 1;
    @Override
    public File export(Collection<String[]> data) {
        String[] categories = {"Series","Issue","Full Title","Variant Description","Publisher","Release Date","Format","Added Date","Creators"};
        String filepath = "data/exported/CSVexport-"+export_index+".csv";

        File file = new File(filepath);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeNext(categories);
            for (String[] comic : data) {
                writer.writeNext(comic);
            }
            export_index++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(filepath);
        return file;
    }

    @Override
    public Collection<String[]> importFile(File file) {
        Collection<String[]> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] comic;
            reader.readNext(); // skip header
            while ((comic = reader.readNext()) != null) {
                data.add(comic);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return data;
    }

    // public static void main(String[] args) {
    //     CSVAdapter csvAdapter = new CSVAdapter();
    //     Collection<String[]> data = new ArrayList<>();
    //     data.add(new String[]{"Absolute Carnage Vs Deadpool","2A","","Regular Tyler Kirkham Cover","Marvel Comics","Sep 11, 2019","Comic","Feb 13, 2020","Frank Tieri | Marcelo Ferreira | Andy Owens"});
    //     data.add(new String[]{"Absolute Carnage Vs Deadpool","2B","","Variant Ed McGuinness Cover","Marvel Comics","Sep 11, 2019","Comic","Feb 13, 2020","Frank Tieri | Marcelo Ferreira | Andy Owens"});

    //     File file = csvAdapter.export(data);
    //     Collection<String[]> importedData = csvAdapter.importFile(file);
    //     for (String[] comic : importedData) {
    //         for (String field : comic) {
    //             System.out.print(field + " ");
    //         }
    //         System.out.println();
    //     }

    //     System.out.println(file);
    // }
}
