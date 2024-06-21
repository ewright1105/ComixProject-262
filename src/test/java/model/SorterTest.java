package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Comic;
import model.searchsort.SortByDefault;
import model.searchsort.SortByNewest;
import model.searchsort.SortByOldest;

public class SorterTest {

    private Comic c1 = new Comic("Marvel", "Spiderman", 2, "1", "Jan 1986");
    private Comic c2 = new Comic("Marvel", "Hulk", 1, "1", "Feb 1999");
    private Comic c3 = new Comic("DC", "Superman", 1, "1", "Jun 2001");
    private Comic c4 = new Comic("DC", "Batman", 1, "1", "May 1983");
    private Comic c5 = new Comic("Dark Horse", "Umbrella Academy", 1, "1", "Aug 1980");
    private Comic c6 = new Comic("Marvel", "Spiderman", 1, "1", "Feb 1986");
    private Comic c7 = new Comic("Marvel", "Spiderman", 2, "3", "Jan 1987");

    @Test
    public void testDefaultSort(){
        List<Comic> comicList = Arrays.asList(c1, c2, c6, c7);

        List<Comic> correctComicList = Arrays.asList(c2, c6, c1, c7);

        SortByDefault defaultSort = new SortByDefault();
        defaultSort.sort(comicList);
        assertEquals(correctComicList, comicList);
    }

    @Test
    public void testOldestSort(){
        List<Comic> comicList = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        List<Comic> correctComicList = Arrays.asList(c5, c4, c1, c6, c7, c2, c3);

        SortByOldest oldestSort = new SortByOldest();
        oldestSort.sort(comicList);
        assertEquals(correctComicList, comicList);
    }

    @Test
    public void testNewestSort(){
        List<Comic> comicList = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        List<Comic> correctComicList = Arrays.asList(c3, c2, c7, c6, c1, c4, c5);

        SortByNewest newestSort = new SortByNewest();
        newestSort.sort(comicList);
        assertEquals(correctComicList, comicList);
    }
    


}