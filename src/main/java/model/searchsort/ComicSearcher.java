package model.searchsort;

import model.Comic;

import java.util.List;

public interface ComicSearcher {
    // Buttons for each search strategy -> calls setSearcher()
    // For exact match and partial match, have a drop-down to select what to search by -> Searches based on the passed in Enum
    //      Pass in "OTHER" enum value for gaps, runs, marks ?
    public List<Comic> search(List<Comic> comics);
}
