package model.searchsort;

import model.Comic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartialMatchSearch implements ComicSearcher {

    private String searchType;
    private String search;

    public PartialMatchSearch(String searchType, String search){
        this.searchType = searchType;
        this.search = search;
    }

    @Override
    public List<Comic> search(List<Comic> comics) {
        List<Searcher> results = Searcher.stream().filter(e -> e.getSearch().equals(searchType))
                .collect(Collectors.toList());
        int searchId = results.get(0).getId();
        List<Comic> matches = new ArrayList<>();
        comics.forEach((comic) -> {
            List<String> newComicVals = comic.getDetails();
            if (newComicVals.get(searchId).contains(search)) {
                matches.add(comic);
            }
        });
        return matches;
    }
}