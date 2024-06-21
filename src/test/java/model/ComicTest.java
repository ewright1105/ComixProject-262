package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ComicTest {

    @Test
    public void newComicTest() {
        // Setup
        String expectedPublisher = "Marvel";
        String expectedSeriesTitle = "The Uncanny X-Men";
        int expectedVolNum = 1;
        String expectedIssueNum = "1";
        String expectedPublicationDate = "Sept. 1963";


        // Invoke
        Comic xmenComic = new Comic(expectedPublisher, expectedSeriesTitle, 1, expectedIssueNum, expectedPublicationDate);
        String actualPublisher = xmenComic.getPublisher();
        String actualSeriesTitle = xmenComic.getSeriesTitle();
        String actualPublicationDate = xmenComic.getPublicationDate();

        // Analyze
        assertEquals(expectedPublisher, actualPublisher);
        assertEquals(expectedSeriesTitle, actualSeriesTitle);
        assertEquals(expectedVolNum, 1);
        assertEquals(expectedIssueNum, "1");
        assertEquals(expectedPublicationDate, actualPublicationDate);
    }

    @Test
    public void equalComics() {
        // Setup
        String expectedPublisher = "Marvel";
        String expectedSeriesTitle = "Fantastic Four";
        int expectedVolNum = 52;
        String expectedIssueNum = "1";
        String expectedPublicationDate = "July 1st, 1966";

        // Invoke
        Comic ffComic1 = new Comic(expectedPublisher, expectedSeriesTitle, expectedVolNum, expectedIssueNum, expectedPublicationDate);
        Comic ffComic2 = new Comic(expectedPublisher, expectedSeriesTitle, expectedVolNum, expectedIssueNum, expectedPublicationDate);

        // Analyze
        assertEquals(ffComic1, ffComic2);
    }

    @Test
    public void creatorsPrincipalCharactersDescriptionValueTest() {
        // Setup
        String expectedBatmanCreators = "Bob Kane|Bill Finger";

        String expectedBatmanCharacter = "Batman";
    
        String expectedDescription = "Introducing Batman! Gotham's Greatest Detective!";

        BigDecimal expectedValue = new BigDecimal(0.50);

        Comic batmanComic = new Comic("DC", "Detective Comics", 1, "52", "May. 1933", expectedBatmanCreators, expectedBatmanCharacter, expectedDescription, expectedValue);

        // Invoke
        String actualBatmanCreators = batmanComic.getCreators();
        String actualBatmanCharacters = batmanComic.getPrincipalCharacters();
        String actualDescription = batmanComic.getDescription();
        BigDecimal actualValue = batmanComic.getComicValue();


        // Analyze
        assertEquals(expectedBatmanCreators, actualBatmanCreators);
        assertEquals(expectedBatmanCharacter, actualBatmanCharacters);
        assertEquals(expectedDescription, actualDescription);
        assertEquals(expectedValue, actualValue);

    }
}