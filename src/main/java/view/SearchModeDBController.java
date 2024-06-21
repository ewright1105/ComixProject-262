package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.searchsort.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SearchModeDBController implements Initializable {
    private final String[] searchModes= {"Exact", "Partial", "Issue"};
    private final String[] searchFields = {"Series Titles", "Story Titles", "Publishers", "Publish Dates", "Creators"};

    private Map<String, ComicSearcher> searcherMap = new HashMap<String, ComicSearcher>() {{
        put("Exact", new ExactMatchSearch("Series Titles", ""));
        put("Partial", new PartialMatchSearch("Series Titles", ""));
        put("Issue", new ExactMatchSearch("Issue Numbers", "0"));
        put("Gaps", new GapSearch());
        put("Runs", new RunSearch());
    }};

    @FXML
    private ChoiceBox<String> searchMode;
    @FXML
    private ChoiceBox<String> searchField;
    @FXML
    private TextField searchQuery;

    private String selectedSearchMode;

    private String selectedSearchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.getItems().addAll(searchFields);
        searchField.setOnAction(this::setSearchField);

        searchMode.getItems().addAll(searchModes);
        searchMode.setOnAction(this::setSearchMode);
    }

    public ComicSearcher createSearcher(){
        String query = this.searchQuery.getText();
        switch (selectedSearchMode) {
            case "Exact":
                searcherMap.put("Exact", new ExactMatchSearch(selectedSearchField, query));
                break;
            case "Partial":
                searcherMap.put("Partial", new PartialMatchSearch(selectedSearchField, query));
                break;
            default:
                searcherMap.put("Issue", new ExactMatchSearch("Issue Numbers", query));
                break;
        }
        return searcherMap.get(selectedSearchMode);
    }
    public void setSearchMode(ActionEvent event){
        this.selectedSearchMode = searchMode.getValue();
    }
    public void setSearchField(ActionEvent event){
        this.selectedSearchField = searchField.getValue();
    }

}
