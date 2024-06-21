package model.searchsort;

import model.Comic;
import model.searchsort.ComicSorter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SortByNewest implements ComicSorter, Comparator<Comic> {

    @Override
    public void sort(List<Comic> collection) {
        collection.sort(this);
    }

    @Override
    public int compare(Comic o1, Comic o2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
        YearMonth date1 = YearMonth.parse(o1.getPublicationDate(), formatter);
        YearMonth date2 = YearMonth.parse(o2.getPublicationDate(), formatter);
        if (date1.equals(date2)){
            return 0;
        }
        return -1 * (date1.compareTo(date2));
    }
    
}
//        String[] date1 = o1.getPublicationDate().split(" ");
//        String[] date2 = o2.getPublicationDate().split(" ");