package view;

import com.opencsv.exceptions.CsvException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import model.UserProxy;
import persistence.UserDAO;
import persistence.UserFileDAO;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class LoginController {
    @FXML
    private TextField nameTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final String GUEST_LANDING = "src/main/java/view/DBPage.fxml";

    private final String USER_LANDING = "src/main/java/view/PCPage.fxml";

    private UserDAO userDAO = new UserFileDAO("data/users.csv");

    public LoginController() throws IOException, CsvException {
    }

    @FXML
    public void login(ActionEvent event) throws IOException, CsvException {

        String username = nameTextField.getText();
        FXMLLoader loader;
        User user;
        if (username.equalsIgnoreCase("guest")){
            loader = loadFile(GUEST_LANDING);
            DBController dbController = loader.getController();
            user = new UserProxy("guest");
            dbController.setUser(user);
        } else {
            loader = loadFile(USER_LANDING);
            PCController pcController = loader.getController();
            user = new UserProxy(username);
            pcController.setUser(user);
            pcController.displayName(username);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void register(ActionEvent event) throws IOException, CsvException {
        String username = nameTextField.getText();
        userDAO.createUser(username);
        this.login(event);

    }

    private FXMLLoader loadFile(String filePath) throws IOException {
        URL login = new File(filePath).toURI().toURL();
        FXMLLoader loader = new FXMLLoader(login);
        root = loader.load();
        return loader;
    }



//    public void switchScene1(ActionEvent event) throws IOException {
//        URL login = new File("src/main/java/view/LoginPage.fxml").toURI().toURL();
//        root = FXMLLoader.load(login);
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void switchScene2(ActionEvent event) throws IOException {
//        URL login = new File("src/main/java/view/PCPage.fxml").toURI().toURL();
//        root = FXMLLoader.load(login);
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
