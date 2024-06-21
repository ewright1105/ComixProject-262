package view;

import java.io.File;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.AllComics;
import model.CSVAdapter;
import model.DatabaseAdapter;
import model.JSONAdapter;
import model.User;
import model.XMLAdapter;
import javafx.scene.Node;



public class DBAdapterController {
    private final CSVAdapter csvAdapter = Main.csvAdapter;
    private final JSONAdapter jsonAdapter = Main.jsonAdapter;
    private final XMLAdapter xmlAdapter = Main.xmlAdapter;

    private User user;
    private File dbFile;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
        public void exportToCSV(ActionEvent event) {
            //redundant but neccessary, as we are going to gather the data from the csv file for every export and convert it as needed
            this.dbFile = new File("data/comics.csv");
            Collection<String[]> data = csvAdapter.importFile(dbFile);
            csvAdapter.export(data);
        }

    public Collection<String[]> importFromCSV(File file) {
        return csvAdapter.importFile(file);
    }

    @FXML
    public File exportToJSON(ActionEvent event) {
        this.dbFile = new File("data/comics.csv");
        Collection<String[]> data =  csvAdapter.importFile(dbFile);
        return jsonAdapter.export(data);
    }

    public Collection<String[]> importFromJSON(File file) {
        return jsonAdapter.importFile(file);
    }

    @FXML
    public File exportToXML(ActionEvent event) {
        this.dbFile = new File("data/comics.csv");
        Collection<String[]> data = csvAdapter.importFile(dbFile);
        return xmlAdapter.export(data);
    }

    public Collection<String[]> importFromXML(File file) {
        return xmlAdapter.importFile(file);
    }
}
