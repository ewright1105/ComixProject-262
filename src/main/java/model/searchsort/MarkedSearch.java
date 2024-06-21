package model.searchsort;

import model.Comic;

import java.util.List;

public class MarkedSearch implements ComicSearcher{

    @Override
    public List<Comic> search(List<Comic> comics) {
        return comics.stream().filter(Comic::getMarkedStatus).toList();
    }
}
