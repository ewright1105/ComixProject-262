package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class AdapterTest {
    private CSVAdapter csvAdapter = new CSVAdapter();
    private JSONAdapter jsonAdapter = new JSONAdapter();
    private XMLAdapter xmlAdapter = new XMLAdapter();
    
    private Collection<String[]> generateTestData() {
        Collection<String[]> data = new ArrayList<>();
        //"The Amazing Spider-Man, Vol. 5",77A,"Beyond, Chapter Three","Regular Arthur Adams Cover","Marvel Comics","Oct 27, 2021",Comic,"Nov 03, 2021","Kelly Thompson | Sara Pichelli"
        data.add(new String[]{"The Amazing Spider-Man, Vol. 5","77A","Beyond, Chapter Three","Regular Arthur Adams Cover","Marvel Comics","Oct 27, 2021","Comic","Nov 03, 2021","Kelly Thompson | Sara Pichelli"});
        return data;
    }
    @Test 
    public void testCSVAdapterExport() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file = csvAdapter.export(collection);
        String expectedFilePath = "data/exported/CSVexport-1.csv";

        // Invoke
        String actualFilePath = file.getPath();  

        // Analyze
        assertEquals(expectedFilePath, actualFilePath);
    } 

    @Test
    public void testCSVAdapterMultipleExports() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file1 = csvAdapter.export(collection);
        File file2 = csvAdapter.export(collection);
        String expectedFilePath1 = "data/exported/CSVexport-1.csv";
        String expectedFilePath2 = "data/exported/CSVexport-2.csv";

        // Invoke
        String actualFilePath1 = file1.getPath();
        String actualFilePath2 = file2.getPath();

        // Analyze
        assertEquals(expectedFilePath1, actualFilePath1);
        assertEquals(expectedFilePath2, actualFilePath2);
    }

    @Test 
    public void testCSVAdapterImport() {
        // Setup
        Collection<String[]> expectedCollection = generateTestData();
        File file = csvAdapter.export(expectedCollection);

        // Invoke
        Collection<String[]> actualCollection = csvAdapter.importFile(file);

        // Analyze
        assertEquals(expectedCollection.size(), actualCollection.size());
        String[] expectedData = expectedCollection.iterator().next();
        String[] actualData = actualCollection.iterator().next();
        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(expectedData[i], actualData[i]);
        }
    }

    @Test
    public void testJSONAdapterExport() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file = jsonAdapter.export(collection);
        String expectedFilePath = "data/exported/JSONexport-1.json";

        // Invoke
        String actualFilePath = file.getPath();  

        // Analyze
        assertEquals(expectedFilePath, actualFilePath);
    }

    @Test
    public void testJSONAdapterMultipleExports() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file1 = jsonAdapter.export(collection);
        File file2 = jsonAdapter.export(collection);
        String expectedFilePath1 = "data/exported/JSONexport-1.json";
        String expectedFilePath2 = "data/exported/JSONexport-2.json";

        // Invoke
        String actualFilePath1 = file1.getPath();
        String actualFilePath2 = file2.getPath();

        // Analyze
        assertEquals(expectedFilePath1, actualFilePath1);
        assertEquals(expectedFilePath2, actualFilePath2);
    }

    @Test
    public void testJSONAdapterImport() {
        // Setup
        Collection<String[]> expectedCollection = generateTestData();
        File file = jsonAdapter.export(expectedCollection);

        // Invoke
        Collection<String[]> actualCollection = jsonAdapter.importFile(file);

        // Analyze
        assertEquals(expectedCollection.size(), actualCollection.size());
        String[] expectedData = expectedCollection.iterator().next();
        String[] actualData = actualCollection.iterator().next();
        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(expectedData[i], actualData[i]);
        }
    }

    @Test
    public void testXMLAdapterExport() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file = xmlAdapter.export(collection);
        String expectedFilePath = "data/exported/XMLexport-1.xml";

        // Invoke
        String actualFilePath = file.getPath();  

        // Analyze
        assertEquals(expectedFilePath, actualFilePath);
    }
    
    @Test
    public void testXMLAdapterMultipleExports() {
        // Setup
        Collection<String[]> collection = generateTestData();
        File file1 = xmlAdapter.export(collection);
        File file2 = xmlAdapter.export(collection);
        String expectedFilePath1 = "data/exported/XMLexport-1.xml";
        String expectedFilePath2 = "data/exported/XMLexport-2.xml";

        // Invoke
        String actualFilePath1 = file1.getPath();
        String actualFilePath2 = file2.getPath();

        // Analyze
        assertEquals(expectedFilePath1, actualFilePath1);
        assertEquals(expectedFilePath2, actualFilePath2);
    }

    @Test
    public void testXMLAdapterImport() {
        // Setup
        Collection<String[]> expectedCollection = generateTestData();
        File file = xmlAdapter.export(expectedCollection);

        // Invoke
        Collection<String[]> actualCollection = xmlAdapter.importFile(file);

        // Analyze
        assertEquals(expectedCollection.size(), actualCollection.size());
        String[] expectedData = expectedCollection.iterator().next();
        String[] actualData = actualCollection.iterator().next();
        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(expectedData[i], actualData[i]);
        }
    }
}
