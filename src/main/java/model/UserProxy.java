package model;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import model.searchsort.ExactMatchSearch;
import model.searchsort.PartialMatchSearch;
import persistence.UserFileDAO;

public class UserProxy implements User {
    private ComixUser realUser;
    private CSVReader reader;
    UserFileDAO dao = new UserFileDAO("data/users.csv");

    public UserProxy(String username) throws IOException, CsvException {
        this.reader = new CSVReader(new FileReader("data/users.csv"));
        List<String[]> userArray = reader.readAll(); // Read CSV for users
        for (String[] user : userArray) {
            String[] pcCSV = user[0].split("[.]");
            if (username.equals(user[1])) {
                ArrayList<Comic> pc = new ArrayList<>();
                for (String com : pcCSV) {
                    String[] comics = com.split(",");
                    if (comics.length >= 6) {
                        pc.add(new Comic(comics[3], comics[0], Integer.parseInt(comics[1]), comics[2],
                                comics[4] + "," + comics[5].substring(0, Math.min(5, comics[5].length()))));
                    }
                }
                realUser = new ComixUser(user[1], Integer.parseInt(user[2]), new PersonalCollection(pc));
            }
        }
    }

    public void searchDB() throws CsvValidationException {
        AllComics database = new AllComics("data/comics.csv");
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "How do you want to search? \nSeries Titles\nStory Titles\nIssue Numbers\nPublishers\nPublish Dates\nCreators\nOr back to go back");
        System.out.println();
        String searchType = scanner.nextLine();
        System.out.println();
        System.out.println();
        System.out.println(
                "Ok, now would you like to search the " + searchType
                        + " exactly or partially or go back");
        String category = scanner.nextLine();
        System.out.println();
        if (category.toLowerCase().contains("exact")
                || category.toLowerCase().contains("part")) {
            if (category.toLowerCase().contains("exact")) {
                System.out.println("Ok, now what do you want to search for?");
                String comicSearch = scanner.nextLine();
                System.out.println();

                database.setSearcher(new ExactMatchSearch(searchType, comicSearch));
                List<Comic> comics = database.search();
                System.out.println();
                System.out.println("These are your results");
                int i = 1;
                ArrayList<Comic> adds = new ArrayList<>();
                for (Comic com : comics) {
                    System.out.println(i + " - " + com);
                    adds.add(com);
                    i++;
                }
                System.out.println();
                System.out.println("Which of these comics would you like to add 1-" + i + "?");
                String value = scanner.nextLine();
                System.out.println();
                realUser.addComic(adds.get(Integer.parseInt(value) - 1));
                System.out.println("This is your new collection\n");
                for (Comic com : realUser.getPersonalCollection().getComics()) {
                    System.out.println(com);
                }

            } else if (category.toLowerCase().contains("part")) {
                System.out.println("Ok, now what do you want to search for?");
                String comicSearch = scanner.nextLine();
                database.setSearcher(new PartialMatchSearch(searchType, comicSearch));
                List<Comic> comics = database.search();
                System.out.println("These are your results");
                int i = 1;
                ArrayList<Comic> adds = new ArrayList<>();
                for (Comic com : comics) {
                    System.out.println(i + " - " + com);
                    adds.add(com);
                    i++;
                }
                System.out.println("Which of these comics would you like to add 1-" + i + "?");
                String value = scanner.nextLine();
                System.out.println();
                realUser.addComic(adds.get(Integer.parseInt(value) - 1));
                System.out.println("This is your new collection\n");
                for (Comic com : realUser.getPersonalCollection().getComics()) {
                    System.out.println(com);
                }
            }
        }
        scanner.close();

    }

    @Override
    public ComixUser addComic(Comic comic) throws IOException, CsvException {
        if (realUser != null) {
            realUser.addComic(comic);
            dao.addToPersonalCollection(dao.getUserWithName(realUser.getUsername()), comic);
            return realUser;
        }
        return null;
    }

    public ComixUser getRealUser() {
        return realUser;
    }

    @Override
    public ComixUser removeComic(Comic comic) throws IOException, CsvException {
        if (realUser != null) {
            realUser.removeComic(comic);
            dao.removeFromPersonalCollection(dao.getUserWithName(realUser.getUsername()), comic);
            return realUser;
        }
        return null;
    }

    @Override
    public ComixUser editComic(Comic comic) {
        if (realUser != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What comic do you want to edit (Series Title)?");
            String editComic = scanner.nextLine();
            System.out.println();
            realUser.getPersonalCollection().setSearcher(new ExactMatchSearch("Series Titles", editComic));
            Comic com = realUser.getPersonalCollection().search().get(0);

            System.out.println("Who is the Publisher?");
            String publisher = scanner.nextLine();
            System.out.println("What is the Series Title?");
            String seriesTitle = scanner.nextLine();
            System.out.println("What is the Volume Number?");
            String volumeNum = scanner.nextLine();
            System.out.println("What is the Issue Number?");
            String issueNum = scanner.nextLine();
            System.out.println("What is the Publication Date? (MMM yyyy)");
            String publicationDate = scanner.nextLine();
            System.out.println("Who are the Creators?");
            String creators = scanner.nextLine();

            realUser.getPersonalCollection().editComic(com, publisher, seriesTitle, Integer.parseInt(volumeNum),
                    issueNum, publicationDate, creators, "", "", BigDecimal.valueOf(0));
            scanner.close();
            return realUser;
        }
        return null;
    }

    @Override
    public ComixUser markComic(Comic comic) {
        if (realUser != null) {
            realUser.getPersonalCollection().markSlabbed(comic);
        }
        return null;
    }

    @Override
    public PersonalCollection getPC() {
        if (realUser != null) {
            return realUser.getPersonalCollection();
        }
        return null;
    }

    @Override
    public boolean checkProxy(){
        return this.realUser != null;
    }

    @Override
    public String getUsername(){
        if (this.realUser != null){
            return realUser.getUsername();
        }
        return null;
    }

}
