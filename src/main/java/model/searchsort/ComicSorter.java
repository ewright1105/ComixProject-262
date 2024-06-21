package model.searchsort;

import model.Comic;

import java.util.Comparator;
import java.util.List;

public interface ComicSorter extends Comparator<Comic> {
    

    public void sort(List<Comic> collection);
}
