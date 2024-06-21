package view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import model.*;
import model.searchsort.ExactMatchSearch;
import model.searchsort.PartialMatchSearch;

public class ComixCLI {
    public static void main(String[] args) throws CsvValidationException {
        System.out.println();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Comix app! Type your name so I know how to address you");
        String name = scanner.nextLine();
        System.out.println();
        System.out.println(
                "Awesome! Thanks " + name
                        + ".\n\nChoose one of these places to be redirected to\n\n->Help\n->Personal Collection\n->Database\n->Quit");
        System.out.println();
        String action = scanner.nextLine();
        System.out.println();
        AllComics comicDatabase = new AllComics("data/comics.csv");
        PersonalCollection personalCollection = new PersonalCollection(new ArrayList<>());

        while (!action.toLowerCase().equals("quit")) {
            if (action.toLowerCase().equals("help")) {
                System.out.println(
                        "Help Menu: \n\n->Help - this menu \n->Quit - Quit \n->Personal Collection - manage your inventory of comics \n->Database - search for comics in our databse of comics\n");
                action = scanner.nextLine();
                System.out.println();

            } else if (action.toLowerCase().equals("personal collection")) {
                System.out.println("\nNow that your in your personal collection " + name
                        + ", you can add a comic, remove a comic, edit a comic, mark a comic, search \nfor a comic or go back.\nWhich interests you?");
                System.out.println();
                String pcAction = scanner.nextLine();
                System.out.println();
                while (!pcAction.toLowerCase().equals("back")) {

                    if (pcAction.toLowerCase().contains("add")) {
                        System.out.println(
                                "So you'd like to add a comic. Would you want to enter the info manually, or select one from the database.");
                        String methodToAdd = scanner.nextLine();
                        System.out.println();
                        if (methodToAdd.toLowerCase().contains("manual")) {
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
                            personalCollection.addComic(new Comic(publisher, seriesTitle, Integer.parseInt(volumeNum),
                                    issueNum, publicationDate));
                            System.out.println("This is your new collection\n");
                            for (Comic com : personalCollection.getComics()) {
                                System.out.println(com);
                            }
                            pcAction = "back";

                        } else if (methodToAdd.toLowerCase().contains("data")) {
                            System.out.println(
                                    "How do you want to search? \nSeries Titles\nStory Titles\nIssue Numbers\nPublishers\nPublish Dates\nCreators\nOr back to go back");
                            System.out.println();
                            String searchType = scanner.nextLine();
                            System.out.println();
                            while (!searchType.toLowerCase().equals("back")) {
                                System.out.println();
                                System.out.println(
                                        "Ok, now would you like to search the " + searchType
                                                + " exactly or partially or go back");
                                String category = scanner.nextLine();
                                System.out.println();
                                if (!category.equals("back") && (category.toLowerCase().contains("exact")
                                        || category.toLowerCase().contains("part"))) {
                                    if (category.toLowerCase().contains("exact")) {
                                        System.out.println("Ok, now what do you want to search for?");
                                        String comicSearch = scanner.nextLine();
                                        System.out.println();
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
                                        System.out.println("Which of these comics would you like to add 1-" + i + "?");
                                        String value = scanner.nextLine();
                                        System.out.println();
                                        personalCollection.addComic(adds.get(Integer.parseInt(value) - 1));
                                        System.out.println("This is your new collection\n");
                                        for (Comic com : personalCollection.getComics()) {
                                            System.out.println(com);
                                        }
                                        pcAction = "back";

                                    } else if (category.toLowerCase().contains("part")) {
                                        System.out.println("Ok, now what do you want to search for?");
                                        String comicSearch = scanner.nextLine();
                                        comicDatabase.setSearcher(new PartialMatchSearch(searchType, comicSearch));
                                        List<Comic> comics = comicDatabase.search();
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
                                        personalCollection.addComic(adds.get(Integer.parseInt(value) - 1));
                                        System.out.println("This is your new collection\n");
                                        for (Comic com : personalCollection.getComics()) {
                                            System.out.println(com);
                                        }
                                        pcAction = "back";
                                    }
                                } else if (category.equals("back")) {
                                    searchType = "back";
                                } else {
                                    System.out.println("Try partial or exact as commands");
                                    System.out.println();
                                }
                            }
                            pcAction = "back";

                        } else if (methodToAdd.toLowerCase().contains("back")) {
                            pcAction = "back";
                        } else {
                            System.out.println("Try database, manual or back as commands");
                            System.out.println();
                        }

                    } else if (pcAction.toLowerCase().contains("remove") && !personalCollection.isEmpty()) {

                        System.out.println("What comic do you want to remove (Series Title)?");
                        String removeComic = scanner.nextLine();
                        System.out.println();
                        ArrayList<Comic> comics = personalCollection.getComics();
                        personalCollection.setSearcher(new ExactMatchSearch("Series Titles", removeComic));
                        Comic comic = personalCollection.search().get(0);
                        personalCollection.removeComic(comic);
                        System.out.println("This is your new collection\n");
                        for (Comic com : personalCollection.getComics()) {
                            System.out.println(com);
                        }
                        pcAction = "back";

                    } else if (pcAction.toLowerCase().contains("edit") && !personalCollection.isEmpty()) {

                        System.out.println("What comic do you want to edit (Series Title)?");
                        String editComic = scanner.nextLine();
                        System.out.println();
                        ArrayList<Comic> comics = personalCollection.getComics();
                        personalCollection.setSearcher(new ExactMatchSearch("Series Titles", editComic));
                        Comic comic = personalCollection.search().get(0);

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

                        personalCollection.editComic(comic, publisher, seriesTitle, Integer.parseInt(volumeNum),
                                issueNum, publicationDate, creators, "", "", BigDecimal.valueOf(0));
                        System.out.println("This is your new collection\n");
                        for (Comic com : personalCollection.getComics()) {
                            System.out.println(com);
                        }
                        pcAction = "back";
                    } else if (pcAction.toLowerCase().contains("mark") && !personalCollection.isEmpty()) {

                        System.out.println("What comic do you want to mark (Series Title)?");
                        String markComic = scanner.nextLine();
                        ArrayList<Comic> comics = personalCollection.getComics();
                        personalCollection.setSearcher(new ExactMatchSearch("Series Titles", markComic));
                        Comic comic = personalCollection.search().get(0);
                        System.out.println("What grade do you want to give this comic? (1-10)");
                        int grade = Integer.parseInt(scanner.nextLine());
                        personalCollection.markComic(comic, grade, false, "", false);
                        for (Comic com : personalCollection.getComics()) {
                            System.out.println(com);
                        }
                        pcAction = "back";

                    } else if (pcAction.toLowerCase().contains("search")) {

                        System.out.println(
                                "How do you want to search? \nSeries Titles\nStory Titles\nIssue Numbers\nPublishers\nPublish Dates\nCreators\nOr back to go back");
                        String searchType = scanner.nextLine();
                        while (!searchType.toLowerCase().equals("back")) {
                            System.out.println(
                                    "Ok, now would you like to search the " + searchType + " exactly or partially");
                            String category = scanner.nextLine();
                            if (category.equals("back")) {
                                searchType = "back";
                                if (category.toLowerCase().contains("exact")) {
                                    System.out.println("Ok, now what do you want to search for?");
                                    String comicSearch = scanner.nextLine();
                                    ArrayList<Comic> comics = personalCollection.getComics();
                                    personalCollection.setSearcher(new ExactMatchSearch(searchType, comicSearch));
                                    List<Comic> results = personalCollection.search();
                                    System.out.println("These are your results");
                                    for (Comic com : results) {
                                        System.out.println(com);
                                    }

                                } else if (category.toLowerCase().contains("part")) {
                                    System.out.println("Ok, now what do you want to search for?");
                                    String comicSearch = scanner.nextLine();
                                    ArrayList<Comic> comics = personalCollection.getComics();
                                    personalCollection.setSearcher(new PartialMatchSearch(searchType, comicSearch));
                                    List<Comic> results = personalCollection.search();
                                    System.out.println("These are your results");
                                    for (Comic com : comics) {
                                        System.out.println(com);
                                    }
                                }
                            } else if (category.equals("back")) {
                                searchType = "back";
                            } else {
                                System.out.println("Try partial or exact as commands");
                                System.out.println();
                            }
                        }
                        pcAction = "back";

                    } else {
                        System.out.println(
                                "Try add, remove, edit, mark, search, or back to return to the main screen.\nIf you tried remove, edit, or mark and are seeing this, your collection must be empty, go add a comic or choose back!");
                        System.out.println();
                        pcAction = scanner.nextLine();
                        System.out.println();
                    }
                }

                System.out.println(
                        "Choose one of these places to be redirected to (case sensitive)\n\n->Help\n->Personal Collection\n->Database\n->Quit");
                action = scanner.nextLine();
                System.out.println();

            } else if (action.toLowerCase().equals("database")) {
                System.out.println();
                System.out.println(
                        "Welcome to the database. How do you want to search? \n-Series Titles\n-Story Titles\n-Issue Numbers\n-Publishers\n-Publish Dates\n-Creators\n-Or back to go back");
                String searchType = scanner.nextLine();
                System.out.println();
                while (!searchType.toLowerCase().equals("back")) {
                    System.out.println();
                    System.out.println(
                            "Ok, now would you like to search the " + searchType + " exactly or partially or go back?");
                    String category = scanner.nextLine();
                    System.out.println();
                    if (category.toLowerCase().contains("exact")) {
                        System.out.println();
                        System.out.println("Ok, now what do you want to search for?");
                        String comicSearch = scanner.nextLine();
                        System.out.println();
                        System.out.println();
                        comicDatabase.setSearcher(new ExactMatchSearch(searchType, comicSearch));
                        List<Comic> comics = comicDatabase.search();
                        System.out.println("These are your results");
                        for (Comic com : comics) {
                            System.out.println(com);
                        }

                    } else if (category.toLowerCase().contains("part")) {
                        System.out.println("Ok, now what do you want to search for?");
                        String comicSearch = scanner.nextLine();
                        System.out.println();
                        comicDatabase.setSearcher(new ExactMatchSearch(searchType, comicSearch));
                        List<Comic> comics = comicDatabase.search();
                        System.out.println("These are your results");
                        for (Comic com : comics) {
                            System.out.println(com);
                        }
                    } else if (category.toLowerCase().contains("back")) {
                        searchType = "back";
                    } else {
                        System.out.println("Try partial, exact or back as commands");
                        System.out.println();
                    }
                }
                System.out.println(
                        "Choose one of these places to be redirected to\n\n->Help\n->Personal Collection\n->Database\n->Quit\n");
                action = scanner.nextLine();
                System.out.println();

            } else {
                System.out.println();
                System.out.println(
                        "That command didnt work. Try one of these commands\n->Help\n->Personal Collection\n->Database\n->Quit\n");
                action = scanner.nextLine();
                System.out.println();

            }
        }
        scanner.close();
        System.out.println("Goodbye " + name + "!");
    }
}
