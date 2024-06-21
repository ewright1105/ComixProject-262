package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.AllComics;
import model.CSVAdapter;
import model.Comic;
import model.JSONAdapter;
import model.User;
import model.XMLAdapter;
import javafx.scene.Node;



public class PCAdapterController {
    private final CSVAdapter csvAdapter = Main.csvAdapter;
    private final JSONAdapter jsonAdapter = Main.jsonAdapter;
    private final XMLAdapter xmlAdapter = Main.xmlAdapter;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
        public void exportToCSV(ActionEvent event) {
            //redundant but neccessary, as we are going to gather the data from the csv file for every export and convert it as needed
            ArrayList<Comic> comics = user.getPC().getComics();
            Collection<String[]> data = new ArrayList<String[]>();
            for (Comic comic : comics) {
                data.add(comic.toString().split(","));
            }
            csvAdapter.export(data);
        }

    public Collection<String[]> importFromCSV(File file) {
        return csvAdapter.importFile(file);
    }

    @FXML
    public File exportToJSON(ActionEvent event) {
        ArrayList<Comic> comics = user.getPC().getComics();
            Collection<String[]> data = new ArrayList<String[]>();
            for (Comic comic : comics) {
                data.add(comic.toString().split(","));
            }
        return jsonAdapter.export(data);
    }

    public Collection<String[]> importFromJSON(File file) {
        return jsonAdapter.importFile(file);
    }

    @FXML
    public File exportToXML(ActionEvent event) {
        ArrayList<Comic> comics = user.getPC().getComics();
        Collection<String[]> data = new ArrayList<String[]>();
        for (Comic comic : comics) {
            data.add(comic.toString().split(","));
        }
        return xmlAdapter.export(data);
    }

    public Collection<String[]> importFromXML(File file) {
        return xmlAdapter.importFile(file);
    }
}
