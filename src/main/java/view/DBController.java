package view;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AllComics;
import model.Comic;
import model.PersonalCollection;
import model.User;
import model.searchsort.ComicSearcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DBController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button searchButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Label svi;
    @FXML
    private Label publisher;
    @FXML
    private Label publishDate;
    @FXML
    private Label creators;

    private AllComics allComics;
    @FXML
    private ListView<Comic> comicListView = new ListView<>();
    private Comic currentComic;

    private User user;
    @FXML
    public void switchToPC(ActionEvent event) throws IOException {
        if (user.checkProxy()){
            URL dbPage = new File("src/main/java/view/PCPage.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(dbPage);
            root = loader.load();
            PCController pcController = loader.getController();
            pcController.setUser(user);
            pcController.displayName(user.getUsername());
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void switchToDBAdapter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DBAdapterPage.fxml"));
            DialogPane adapterDialogPane = loader.load();
            DBAdapterController adapterController = loader.getController();
            adapterController.setUser(user); // Set the user for the adapter controller

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(adapterDialogPane);
            dialog.setTitle("Database Export/Import");

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            dialog.initOwner(stage);

            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        URL loginPage = new File("src/main/java/view/LoginPage.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(loginPage);
        root = loader.load();
//        PCController pcController = loader.getController();
//        pcController.setUser(user);
//        pcController.displayName(user.getUsername());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.allComics = new AllComics("data/comics.csv");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        Collection<Comic> comics = allComics.getRawComics().values();
        comicListView.getItems().addAll(comics);
        comicListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Comic>() {
            @Override
            public void changed(ObservableValue<? extends Comic> observableValue, Comic comic, Comic t1) {
                Comic selectedComic = comicListView.getSelectionModel().getSelectedItem();
                if (selectedComic != null) {
                    currentComic = selectedComic;
                    svi.setText("SVI: " + currentComic.getSeriesTitle() + ", " + currentComic.getVolumeNum() + ", " + currentComic.getIssueNumString());
                    publisher.setText("Publisher: " + currentComic.getPublisher());
                    publishDate.setText("Publish Date: " + currentComic.getPublicationDate());
                    creators.setText("Creators: " + currentComic.getCreators());
                } else {
                    svi.setText("SVI: ");
                    publisher.setText("Publisher: ");
                    publishDate.setText("Publish Date: ");
                    creators.setText("Creators: ");
                }
            }
        });

    }

    public boolean handleSearchDialog() throws IOException {
        try {
            URL searchDialog = new File("src/main/java/view/SearchModeDB.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(searchDialog);
            DialogPane searchDialogPane = loader.load();
            SearchModeDBController smController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(searchDialogPane);
            dialog.setTitle("Search");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                ComicSearcher comicSearcher = smController.createSearcher();
                allComics.setSearcher(comicSearcher);
                return true;
            } else if (clickedButton.get() == ButtonType.CANCEL){
                dialog.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    public void search() throws IOException {
        if(handleSearchDialog()){
            List<Comic> result = allComics.search();
            updateComicListView(result);
        }
    }

    public void updateComicListView(List<Comic> comicList) {
        comicListView.getItems().setAll(comicList);
    }

    public void setUser(User user){
        this.user = user;
    }

    public void addComic() throws IOException, CsvException {
        if (currentComic != null) {
            user.addComic(currentComic);
        }
    }

}
