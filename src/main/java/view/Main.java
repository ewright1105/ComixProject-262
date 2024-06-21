package view;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import model.CSVAdapter;
import model.JSONAdapter;
import model.XMLAdapter;

public class Main extends Application {

    private boolean initialized;
    private static Parent root;

    private LoginController loginController;
    private PCController pcController;
    private DBController dbController;

    static List<AnchorPane> scenes = new ArrayList<>();

    public static CSVAdapter csvAdapter = new CSVAdapter();
    public static JSONAdapter jsonAdapter = new JSONAdapter();
    public static XMLAdapter xmlAdapter = new XMLAdapter();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL login = new File("src/main/java/view/LoginPage.fxml").toURI().toURL();
            URL collection = new File("src/main/java/view/DBPage.fxml").toURI().toURL();
            URL styles = new File("src/main/java/view/comix.css").toURI().toURL();
            root = FXMLLoader.load(login);
            scenes.add((AnchorPane)FXMLLoader.load(login));
            scenes.add((AnchorPane)FXMLLoader.load(collection));

            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(styles.toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
            initialized = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void init(){
        initialized = false;
    }


}
