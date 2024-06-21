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

public class SearchModePCController implements Initializable {

    private final String[] searchModes= {"Exact", "Partial", "Marked", "Gaps", "Runs", "Issue"};
    private final String[] searchFields = {"Series Titles", "Story Titles", "Publishers", "Publish Dates", "Creators"};
    private final String[] sortModes = {"Default", "Newest", "Oldest"};

    private Map<String, ComicSearcher> searcherMap = new HashMap<String, ComicSearcher>() {{
        put("Exact", new ExactMatchSearch("Series Titles", ""));
        put("Partial", new PartialMatchSearch("Series Titles", ""));
        put("Issue", new ExactMatchSearch("Issue Numbers", "0"));
        put("Marked", new MarkedSearch());
        put("Gaps", new GapSearch());
        put("Runs", new RunSearch());
    }};

    private Map<String, ComicSorter> sorterMap = new HashMap<String, ComicSorter>() {{
        put("Default", new SortByDefault());
        put("Newest", new SortByNewest());
        put("Oldest", new SortByOldest());
    }};

    @FXML
    private ChoiceBox<String> searchMode;
    @FXML
    private ChoiceBox<String> searchField;
    @FXML
    private ChoiceBox<String> sortMode;
    @FXML
    private TextField searchQuery;

    private String selectedSearchMode;

    private String selectedSearchField;

    private String selectedSortMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.getItems().addAll(searchFields);
        searchField.setOnAction(this::setSearchField);

        searchMode.getItems().addAll(searchModes);
        searchMode.setOnAction(this::setSearchMode);

        sortMode.getItems().addAll(sortModes);
        sortMode.setOnAction(this::setSortMode);
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
            case "Issue":
                searcherMap.put("Issue", new ExactMatchSearch("Issue Numbers", query));
                break;
            default:
                break;
        }
        return searcherMap.get(selectedSearchMode);
    }

    public ComicSorter createSorter() {
        return sorterMap.get(selectedSortMode);
    }
    public void setSearchMode(ActionEvent event){
        this.selectedSearchMode = searchMode.getValue();
    }
    public void setSortMode(ActionEvent event){
        this.selectedSortMode = sortMode.getValue();
    }
    public void setSearchField(ActionEvent event){
        this.selectedSearchField = searchField.getValue();
    }



}
