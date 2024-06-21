package model;

import model.searchsort.ComicSearcher;
import model.searchsort.ComicSorter;
import model.searchsort.ExactMatchSearch;
import model.searchsort.SortByDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonalCollection {
    private ArrayList<Comic> comics;

    private ComicSorter sorter;

    private ComicSearcher searcher;

    public PersonalCollection(ArrayList<Comic> comics) {

        this.comics = comics;
        this.sorter = new SortByDefault();
        this.searcher = new ExactMatchSearch("Series Title", ""); // Temporary
    }

    public void setSorter(ComicSorter sorter) {
        this.sorter = sorter;
    }

    public void setSearcher(ComicSearcher searcher) {
        this.searcher = searcher;
    }

    public void sort() {
        this.sorter.sort(this.comics);
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public void setComic(ArrayList<Comic> comics) {
        this.comics = comics;
    }

    public void addComic(Comic comic) {
        comics.add(comic);
    }

    public void addComicManually(String publisher, String seriesTitle, int volumeNum, String issueNum,
            String publicationDate, String fullTitle, String creators) {
        Comic newComic = new Comic(publisher, seriesTitle, volumeNum, issueNum, publicationDate, creators, fullTitle);

        comics.add(newComic);
    }

    public void removeComic(Comic comic) {
        // Iterator<Comic> itr = comics.iterator();
        // while (itr.hasNext()) {
        //     Comic com = itr.next();
        //     if (com.getSeriesTitle().equals(comic.getSeriesTitle())) {
        //         itr.remove();
        //     }
        // }
        comics.remove(comic); // Instead of doing the following above, this will remove a specific comic in the PC. 
    }

    public void editComic(Comic comic, String publisher, String seriesTitle, int volumeNum, String issueNum,
            String publicationDate, String creators, String principalCharacters, String description, BigDecimal value) {

        comic.setPublisher(publisher);
        comic.setSeriesTitle(seriesTitle);
        comic.setVolumeNum(volumeNum);
        comic.setIssueNumString(issueNum);
        comic.setPublicationDate(publicationDate);
        comic.setCreators(creators);
        comic.setPrincipalCharacters(principalCharacters);
        comic.setDescription(description);
        comic.setValue(value);
    }

    public void markGraded(Comic comic, int grade) {
        if (grade == 1) {
            if (comic.getValue() != null) {
                if (comic.getValue().doubleValue() == 0.0) {
                    comic.setValue(new BigDecimal(0.10));
                } else if (comic.getValue().doubleValue() > 0.0) {
                    comic.setValue(comic.getValue().multiply(new BigDecimal(0.10)));
                }
            }
        } else if (grade >= 2 && grade <= 10) {
            if (comic.getValue() != null) {
                if (comic.getValue().doubleValue() == 0.0) {
                    comic.setValue(new BigDecimal(Math.log10(grade)));
                } else {
                    double calculatedValue = Math.log10(grade) * comic.getValue().doubleValue();
                    comic.setValue(new BigDecimal(calculatedValue));
                }
            }
        }
    }

  
    public void markSlabbed(Comic comic) {
        if (comic.getValue() != null) {
            comic.setValue(comic.getValue().multiply(new BigDecimal(2.0)));
       }
    }

    public void markComic(Comic comic, int grade, boolean slabbed, String signature, boolean authenticated) {
        comic.addMarks(grade, slabbed, signature, authenticated);
    }


    public boolean isEmpty() {
        return comics.isEmpty();
    }

    public List<Comic> search() {
        List<Comic> results = this.searcher.search(this.comics);
        results.sort(this.sorter);
        return results;
    }


    @Override
    public String toString() {
        String value = "";
        for (int i = 0; i < comics.size(); i++) {
            if (i == comics.size() - 1) {
                return value += comics.get(i).toString();
            }
            value += comics.get(i).toString();
        }
        return value;
    }
}