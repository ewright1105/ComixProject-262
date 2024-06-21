package model.searchsort;

import model.Comic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExactMatchSearch implements ComicSearcher {
    private String searchType;
    private String search;
    public ExactMatchSearch(String searchType, String search){
        this.search = search;
        this.searchType = searchType;
    }
    @Override
    public List<Comic> search(List<Comic> comics) {
        List<Searcher> results = Searcher.stream().filter(e -> e.getSearch().equals(searchType))
                .collect(Collectors.toList()); // Searching enum vals for searchType
        int searchId = results.get(0).getId(); // Assigning the selected enum type
        List<Comic> matches = new ArrayList<>(); // Creating a result list
        comics.forEach((comic) -> { // Iterating through list
            List<String> newComicVals = comic.getDetails(); // List of comic data
            if (newComicVals.get(searchId).equals(search)) { // checking if the current comic has the value associated with the searchType
                matches.add(comic);
            }
        });
        return matches;
    }
}