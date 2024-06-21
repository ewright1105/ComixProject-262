package model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import model.searchsort.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.opencsv.exceptions.CsvValidationException;

import static org.junit.jupiter.api.Assertions.*;


public class SearchTest {
    AllComics comics;

    @BeforeEach
    public void setUp() throws CsvValidationException {
        comics = new AllComics("data/testComics.csv");
    }
    @Test
    public void testExactMatchSearchSuccessful() throws CsvValidationException {
        // Setup
        String expectedPublisher = "Marvel Comics";
        String expectedSeriesTitle = "Fantastic Four, Vol. 6";

        // Invoke
        comics.setSearcher(new ExactMatchSearch("Series Titles", expectedSeriesTitle));
        Comic actualComic = comics.search().get(0);
        String actualPublisher = actualComic.getPublisher();
        String actualSeriesTitle = actualComic.getSeriesTitle();

        // Analyze
        assertEquals(expectedPublisher, actualPublisher);
        assertEquals(expectedSeriesTitle, actualSeriesTitle);
    }

    @Test
    public void testExactMatchSearchUnsuccessful() throws CsvValidationException {
        // Setup - nothing to set up

        // Invoke
        comics.setSearcher(new ExactMatchSearch("Series Titles", "Fantastic Four, Vol. 7"));
        List<Comic> actualComics = comics.search();

        // Analyze
        assertEquals(0, actualComics.size());
    }

    @Test 
    public void testPartialMatchSearch() throws CsvValidationException {
        // Setup
        int expectedLength1A = 1;
        int expectedLength1B = 0;
        int expectedLength2A = 7;
        int expectedLength2B = 5;
        int expectedLength3A = 5;
        int expectedLength3B = 0;

        // Invoke
        comics.setSearcher(new PartialMatchSearch("Series Titles", "Fantastic"));
        List<Comic> actualComics1A = comics.search();
        comics.setSearcher(new PartialMatchSearch("Publishers", "Fantastic"));
        List<Comic> actualComics1B = comics.search();
        comics.setSearcher(new PartialMatchSearch("Series Titles", "a"));
        List<Comic> actualComics2A = comics.search();
        comics.setSearcher(new PartialMatchSearch("Publishers", "a"));
        List<Comic> actualComics2B = comics.search();
        comics.setSearcher(new PartialMatchSearch("Series Titles", "Vol."));
        List<Comic> actualComics3A = comics.search();
        comics.setSearcher(new PartialMatchSearch("Publishers", "Vol."));
        List<Comic> actualComics3B = comics.search();

        // Analyze
        assertEquals(expectedLength1A, actualComics1A.size());
        assertEquals(expectedLength1B, actualComics1B.size());
        assertEquals(expectedLength2A, actualComics2A.size());
        assertEquals(expectedLength2B, actualComics2B.size());
        assertEquals(expectedLength3A, actualComics3A.size());
        assertEquals(expectedLength3B, actualComics3B.size());

    }

    @Test
    public void testRunSearch(){
        Comic c1 = new Comic("Marvel", "Spiderman", 1, "1", "Jan 1986");
        Comic c2 = new Comic("Marvel", "Spiderman", 1, "2", "Feb 1999");
        Comic c3 = new Comic("Marvel", "Spiderman", 1, "3", "Jan 1986");
        Comic c4 = new Comic("Marvel", "Spiderman", 1, "4", "Feb 1999");
        Comic c5 = new Comic("Marvel", "Spiderman", 1, "5", "Feb 1999");
        Comic c6 = new Comic("Marvel", "Spiderman", 1, "6", "Jan 1986");
        Comic c7 = new Comic("Marvel", "Spiderman", 1, "7", "Feb 1999");
        Comic c8 = new Comic("Marvel", "Spiderman", 1, "8", "Feb 1999");
        Comic c9 = new Comic("Marvel", "Spiderman", 1, "9", "Feb 1999");
        Comic c10 = new Comic("Marvel", "Spiderman", 1, "10", "Feb 1999");
        Comic c11 = new Comic("Marvel", "Spiderman", 1, "11", "Feb 1999");
        Comic c12 = new Comic("Marvel", "Spiderman", 1, "12", "Feb 1999");
        Comic c13 = new Comic("Marvel", "Spiderman", 1, "13", "Feb 1999");

        List<Comic> run1 = Arrays.asList(c1, c2, c3, c4, c5, c6,c7, c8, c9, c10, c11, c12);
        List<Comic> run2 = Arrays.asList(c6, c7);
        List<Comic> run3 = Arrays.asList(c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13);
        List<Comic> run4 = Arrays.asList(c1, c2, c3, c4, c6, c7, c8, c9, c10, c11, c12, c13);

        RunSearch runSearch = new RunSearch();

        List<Comic> result1 = runSearch.search(run1);
        List<Comic> result2 = runSearch.search(run2);
        List<Comic> result3 = runSearch.search(run3);
        List<Comic> result4 = runSearch.search(run4);

        assertEquals(12, result1.size());
        assertEquals(0, result2.size());
        assertEquals(12, result3.size());
        assertEquals(0, result4.size());
    }

    @Test
    public void testGapSearch(){
        Comic c1 = new Comic("Marvel", "Spiderman", 1, "1", "Jan 1986");
        Comic c2 = new Comic("Marvel", "Spiderman", 1, "2", "Feb 1999");
        Comic c3 = new Comic("Marvel", "Spiderman", 1, "3", "Jan 1986");
        Comic c4 = new Comic("Marvel", "Spiderman", 1, "4", "Feb 1999");
        Comic c5 = new Comic("Marvel", "Spiderman", 1, "5", "Feb 1999");
        Comic c6 = new Comic("Marvel", "Spiderman", 1, "6", "Jan 1986");
        Comic c7 = new Comic("Marvel", "Spiderman", 1, "7", "Feb 1999");
        Comic c8 = new Comic("Marvel", "Spiderman", 1, "8", "Feb 1999");
        Comic c9 = new Comic("Marvel", "Spiderman", 1, "9", "Feb 1999");
        Comic c10 = new Comic("Marvel", "Spiderman", 1, "10", "Feb 1999");
        Comic c11 = new Comic("Marvel", "Spiderman", 1, "11", "Feb 1999");
        Comic c12 = new Comic("Marvel", "Spiderman", 1, "12", "Feb 1999");
        Comic c13 = new Comic("Marvel", "Spiderman", 1, "13", "Feb 1999");
        Comic c14 = new Comic("Marvel", "Spiderman", 1, "14", "Feb 1999");
        Comic c15 = new Comic("Marvel", "Spiderman", 1, "15", "Feb 1999");

        List<Comic> run1 = Arrays.asList(c1, c2, c3, c4, c5, c6,c7, c8);
        List<Comic> run2 = Arrays.asList(c1, c2, c3, c4, c6, c7, c8, c9, c10, c11, c12, c13);
        List<Comic> run3 = Arrays.asList(c1, c2, c3, c4, c7, c8, c9, c10, c11, c12, c13);
        List<Comic> run4 = Arrays.asList(c1, c2, c3, c4, c9, c10, c11, c12, c13, c14, c15);

        GapSearch gapSearch = new GapSearch();

        List<Comic> result1 = gapSearch.search(run1);
        List<Comic> result2 = gapSearch.search(run2);
        List<Comic> result3 = gapSearch.search(run3);
        List<Comic> result4 = gapSearch.search(run4);

        assertEquals(0, result1.size());
        assertEquals(12, result2.size());
        assertEquals(11, result3.size());
        assertEquals(0, result4.size());

    }

    @Test
    public void testMarkedSearch(){
        Comic c1 = new Comic("Marvel", "Spiderman", 1, "1", "Jan 1986");
        Comic c2 = new Comic("Marvel", "Spiderman", 1, "2", "Feb 1999");
        Comic c3 = new Comic("Marvel", "Spiderman", 1, "3", "Jan 1986");
        Comic c4 = new Comic("Marvel", "Spiderman", 1, "4", "Feb 1999");

        c1.addMarks(10, false, "", false);

        List<Comic> collection1 = Arrays.asList(c1, c2, c3, c4);

        MarkedSearch markedSearch = new MarkedSearch();
        List<Comic> result = markedSearch.search(collection1);

        assertEquals(1, result.size());
        assertTrue(result.contains(c1));
        assertFalse(result.contains(c2));
        assertFalse(result.contains(c3));
        assertFalse(result.contains(c4));
    }
    
}
