package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.searchsort.ComicSearcher;
import model.searchsort.ComicSorter;
import model.searchsort.ExactMatchSearch;
import model.searchsort.SortByDefault;

public class AllComics {
    private Map<Integer, Comic> rawComics;

    private ComicSearcher searcher;

    private ComicSorter sorter;

    public AllComics(String filepath) throws CsvValidationException {
        this.rawComics = new HashMap<>();
        this.searcher = new ExactMatchSearch("Series Title", "");
        this.sorter = new SortByDefault();

        try (CSVReader csvReader = new CSVReader(new FileReader(filepath))) {
            csvReader.skip(1);
            int id = 1;
            String[] line = csvReader.readNext();
            while (line != null) {

                String seriesTitle = line[0];
                String issueNum = line[1];
                String storyTitle = line[2];
                String publisher = line[4];
                String publishDate;
                if (!line[5].contains(",")) {
                    publishDate = line[5].replace(" ", " 01, ");
                } else {
                    publishDate = line[5];
                }
                String creators = line[8];
                int volumeNum = 1;

                if (seriesTitle.contains("vol.")) {
                    volumeNum = Integer.parseInt(seriesTitle.substring(seriesTitle.length() - 1));
                }

                Comic comic = new Comic(publisher, seriesTitle, volumeNum, issueNum, publishDate, creators,
                        storyTitle);
                this.rawComics.put(id, comic);

                line = csvReader.readNext();
                id++;
            }
            csvReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSearcher(ComicSearcher searcher) {
        this.searcher = searcher;
    }

    public void setSorter(ComicSorter sorter){
        this.sorter = sorter;
    }

    public Map<Integer, Comic> getRawComics() {
        return rawComics;
    }

    public List<Comic> search() {
        List<Comic> searchList = new ArrayList<>(this.rawComics.values());
        searchList.sort(this.sorter);
        return this.searcher.search(searchList);
    }
}
