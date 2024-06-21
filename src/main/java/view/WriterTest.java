package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvException;

import model.AllComics;
import model.Comic;
import model.UserProxy;
import model.searchsort.ExactMatchSearch;
import persistence.UserFileDAO;

public class WriterTest {
    public static void main(String[] args) throws IOException, CsvException {
        System.out.println();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Comix app! Give me a filepath");
        UserFileDAO dao = new UserFileDAO("data/users.csv");
        System.out.println("Welcome to the Comix app! Give me your username");
        String name = scanner.nextLine();
        UserProxy proxy = new UserProxy(name);
        System.out.println("Ok, now what do you want to search for?");
        String comicSearch = scanner.nextLine();
        System.out.println();
        AllComics comicDatabase = new AllComics("data/testComics.csv");
        comicDatabase.setSearcher(new ExactMatchSearch("Series Titles", comicSearch));
        List<Comic> comics = comicDatabase.search();
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
        System.out.println("Which of these comics would you like to add 1-" + (i - 1) + "?");
        String value = scanner.nextLine();
        System.out.println();
        // dao.addToPersonalCollection(dao.getUserWithName(name),
        // adds.get(Integer.parseInt(value) - 1));
        proxy.addComic(adds.get(Integer.parseInt(value) - 1));
        for (Comic com : proxy.getRealUser().getPersonalCollection().getComics()) {
            System.out.println(com);
        }
        scanner.close();

    }
}