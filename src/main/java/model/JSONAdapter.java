package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
public class JSONAdapter implements DatabaseAdapter{
    public int export_index = 1;
    @Override
    public File export(Collection<String[]> data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filepath = "data/exported/JSONexport-" + export_index + ".json";
        JsonArray jsonArray = new JsonArray();
        for (String[] array : data) {
            JsonObject comicObject = new JsonObject();

            comicObject.addProperty("Series", getValue(array, 0));
            comicObject.addProperty("Issue", getValue(array, 1));
            comicObject.addProperty("Full Title", getValue(array, 2));
            comicObject.addProperty("Variant Description", getValue(array, 3));
            comicObject.addProperty("Publisher", getValue(array, 4));
            comicObject.addProperty("Release Date", getValue(array, 5));
            comicObject.addProperty("Format", getValue(array, 6));
            comicObject.addProperty("Added Date", getValue(array, 7));
            comicObject.addProperty("Creators", getValue(array, 8));

            jsonArray.add(comicObject);
        }
        File file = new File(filepath);
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(jsonArray, writer);
            export_index++;
            System.out.println(filepath);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
        return file;
    }

    private String getValue(String[] array, int index) {
        return (index >= 0 && index < array.length) ? array[index] : "";
    }

    @Override
    public Collection<String[]> importFile(File file) {
        Collection<String[]> data = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonArray jsonArray = gson.fromJson(new FileReader(file), JsonArray.class);
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject comicObject = jsonArray.get(i).getAsJsonObject();
                String[] array = new String[9];
                array[0] = comicObject.get("Series").getAsString();
                array[1] = comicObject.get("Issue").getAsString();
                array[2] = comicObject.get("Full Title").getAsString();
                array[3] = comicObject.get("Variant Description").getAsString();
                array[4] = comicObject.get("Publisher").getAsString();
                array[5] = comicObject.get("Release Date").getAsString();
                array[6] = comicObject.get("Format").getAsString();
                array[7] = comicObject.get("Added Date").getAsString();
                array[8] = comicObject.get("Creators").getAsString();
                data.add(array);
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON from file: " + e.getMessage());
        }
        return data;
    }
}
