package view;

import com.opencsv.exceptions.CsvException;
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
import model.Comic;
import model.PersonalCollection;
import model.User;
import model.searchsort.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PCController implements Initializable{
    @FXML
    private Label nameLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button searchButton;
    @FXML
    private Button editButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ListView<Comic> comicListView = new ListView<>();

    @FXML
    private Label svi;
    @FXML
    private Label publisher;
    @FXML
    private Label publishDate;
    @FXML
    private Label creators;
    @FXML
    private Label value;
    
    private PersonalCollection personalCollection;

    private User user;

    private ArrayList<Comic> comics = new ArrayList<>() {{ // Temporary list for testing
        add(new Comic("Marvel", "Spiderman", 1, "1", "Jan 1986"));
        add(new Comic("Marvel", "Spiderman", 1, "2", "Feb 1999"));
        add(new Comic("Marvel", "Spiderman", 1, "3", "Jan 1986"));
        add(new Comic("Marvel", "Spiderman", 1, "4", "Feb 1999"));
    }};

    private Comic currentComic;


    // TODO: Make adapter maps


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personalCollection = new PersonalCollection(comics);

//        adaptMode.getItems().addAll(adaptModes);
//        fileMode.getItems().addAll(fileModes);

//        ContextMenu contextMenu = new ContextMenu();
//        MenuItem editItem = new MenuItem("Edit Comic");
//        editItem.setOnAction(event -> editComicAction());

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
                    value.setText("Value: $" + currentComic.getComicValue().toString());
                } else {
                    svi.setText("SVI: ");
                    publisher.setText("Publisher: ");
                    publishDate.setText("Publish Date: ");
                    creators.setText("Creators: ");
                    value.setText("Value: ");
                }
            }
        });
    }

    public void displayName(String name){
        nameLabel.setText("COMIX - " + name.toUpperCase() + "'S COLLECTION");
    }

    @FXML
    public void switchToDB(ActionEvent event) throws IOException {
        URL dbPage = new File("src/main/java/view/DBPage.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(dbPage);
        root = loader.load();
        DBController dbController = loader.getController();
        dbController.setUser(user);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToPCAdapter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PCAdapterPage.fxml"));
            DialogPane adapterDialogPane = loader.load();
            PCAdapterController adapterController = loader.getController();
            adapterController.setUser(user); // Set the user for the adapter controller

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(adapterDialogPane);
            dialog.setTitle("Personal Collection Export/Import");

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
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean handleSearchDialog() throws IOException {
        try {
            URL searchDialog = new File("src/main/java/view/SearchModePC.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(searchDialog);
            DialogPane searchDialogPane = loader.load();
            SearchModePCController smController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(searchDialogPane);
            dialog.setTitle("Search");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                ComicSearcher comicSearcher = smController.createSearcher();
                ComicSorter comicSorter = smController.createSorter();
                personalCollection.setSearcher(comicSearcher);
                personalCollection.setSorter(comicSorter);
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
    public boolean handleEditDialog() throws IOException {
        try {
            URL editDialog = new File("src/main/java/view/EditComic.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(editDialog);
            DialogPane editDialogPane = loader.load();
            EditComicController ecController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(editDialogPane);
            ecController.setComicToEdit(currentComic);
            dialog.setTitle("Edit Comic");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                Comic oldComic = currentComic;
                currentComic = ecController.saveChanges();
                personalCollection.removeComic(oldComic);
                personalCollection.addComic(currentComic);
                dialog.close();
//                ComicSearcher comicSearcher = ecController.createSearcher();
//                ComicSorter comicSorter = ecController.createSorter();
//                personalCollection.setSearcher(comicSearcher);
//                personalCollection.setSorter(comicSorter);
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
        if(handleSearchDialog()) {
            List<Comic> result = personalCollection.search();
            updateComicListView(result);
        }
    }

    @FXML
    private void removeComicAction() throws IOException, CsvException {
//        Comic selectedComic = comicListView.getSelectionModel().getSelectedItem();
        if (currentComic != null) {
           user.removeComic(currentComic);
           this.setPersonalCollection();
           updateComicListView(personalCollection.getComics());
        } else {
            System.out.println("No comic selected.");
        }
    }

    public void updateComicListView(List<Comic> comicList) {
        comicListView.getItems().setAll(comicList);
    }

    public void setPersonalCollection() {
        this.personalCollection = user.getPC();
        updateComicListView(this.personalCollection.getComics());
    }

    public void setUser(User user){
        this.user = user;
        setPersonalCollection();
    }

}
