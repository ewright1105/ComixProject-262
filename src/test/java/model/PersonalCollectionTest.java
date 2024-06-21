package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonalCollectionTest {
    private PersonalCollection pc;

    @BeforeEach
    public void setUp(){
        pc = new PersonalCollection(new ArrayList<Comic>());
    }

    @Test
    public void testAddComic(){
        // Setup
        Comic comic = new Comic("Marvel", "Fantastic Four", 1, "1", "Nov. 1961");
        int expectedSize = 1;

        // Invoke
        pc.addComic(comic);
        int actualSize = pc.getComics().size();

        // Analyze
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAddComicManually(){
        // Setup
        String publisher = "Marvel Comics";
        String seriesTitle = "Fantastic Four, Vol 6.";
        int volNum = 5;
        String issueNum = "77A";
        String publicationDate = "Oct 27, 2021";
        String fullTitle = "Beyond, Chapter Three";
        String creators = "Kelly Thompson | Sara Pichelli";
        int expectedSize = 1;


        // Invoke
        pc.addComicManually(publisher, seriesTitle, volNum, issueNum, publicationDate, fullTitle, creators);
        int actualSize = pc.getComics().size();

        // Analyze
        assertEquals(expectedSize, actualSize);
        Comic actualComic = pc.getComics().get(0);
        assertEquals(publisher, actualComic.getPublisher());
        assertEquals(seriesTitle, actualComic.getSeriesTitle());
        assertEquals(volNum, actualComic.getVolumeNum());
        assertEquals(issueNum, actualComic.getIssueNumString());
        assertEquals(publicationDate, actualComic.getPublicationDate());
        assertEquals(fullTitle, actualComic.getFullTitle());
        assertEquals(creators, actualComic.getCreators());

    }

    @Test
    public void testRemoveComic(){
        // Setup
        Comic comic = new Comic("Marvel", "Fantastic Four", 1, "1", "Nov. 1961");
        pc.addComic(comic);
        int expectedSize = 0;

        // Invoke
        pc.removeComic(comic);
        int actualSize = pc.getComics().size();

        // Analyze
        assertEquals(expectedSize, actualSize);
    }

    @Test 
    public void testEditComic(){
        // Setup
        //"Fantastic Four, Vol. 6",1M,,"Incentive Alex Ross Variant Cover","Marvel Comics","Aug 08, 2018",Comic,"Jan 19, 2019","Dan Slott | Simone Bianchi | Skottie Young"
        String publisher = "Marvel Comics";
        String seriesTitle = "Fantastic Four, Vol 6.";
        int volNum = 5;
        String issueNum = "77A";
        String publicationDate = "Oct 27, 2021";
        String fullTitle = "Beyond, Chapter Three";
        String creators = "Kelly Thompson | Sara Pichelli";
        String principalCharacters = "Spiderman, Ironman, Thor";
        String description = "The Fantastic Four are back in action!";
        pc.addComicManually(publisher, seriesTitle, volNum, issueNum, publicationDate, fullTitle, creators);
        Comic comic = pc.getComics().get(0);

        //Invoke
        pc.editComic(comic,"DC Comics",seriesTitle,volNum,issueNum,publicationDate,creators,principalCharacters,description,new BigDecimal(5.00));

        //Analyze
        assertEquals("DC Comics", comic.getPublisher());
        assertEquals(principalCharacters, comic.getPrincipalCharacters());
        assertEquals(description, comic.getDescription());
    }

    @Test
    public void testMarkGradedWithCurrentValue0(){
        // Setup
        Comic comic = new Comic("Marvel", "Fantastic Four", 1, "1", "Nov. 1961");
        pc.addComic(comic);
        int grade = 6;
        BigDecimal expectedValue = new BigDecimal(Math.log10(grade));

        // Invoke
        pc.markComic(comic, grade, false, "", 0);
        BigDecimal actualValue = comic.getComicValue();

        // Analyze
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void testMarkGradedWithCurrentValueGreaterThan0(){
        // Setup
        Comic comic = new Comic("Marvel", "Fantastic Four", 1, "1", "Nov. 1961");
        comic.setValue(new BigDecimal(5.00));
        pc.addComic(comic);
        int grade = 6;
        BigDecimal expectedValue = (new BigDecimal(Math.log10(grade)*comic.getComicValue().doubleValue()));

        // Invoke
        pc.markComic(comic, grade, false, "", 0);
        BigDecimal actualValue = comic.getComicValue();

        // Analyze
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testMarkSlabbed(){
        // Setup
        Comic comic = new Comic("Marvel", "Fantastic Four", 1, "1", "Nov. 1961");
        comic.setValue(new BigDecimal(5.00));
        pc.addComic(comic);
        BigDecimal expectedValue = comic.getComicValue().multiply(new BigDecimal(2));

        // Invoke
        pc.markComic(comic, 10, true, "", 0);
        BigDecimal actualValue = comic.getComicValue();

        // Analyze
        assertEquals(expectedValue, actualValue);
    }

    //already tested getExactMatches and getPartialMatches in SearchTest
}
