package model;

import model.searchsort.ExactMatchSearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class ComixUser implements User {
    private PersonalCollection personalCollection;
    private String username;
    private int id;

    public ComixUser(String username, int id) {
        this.id = id;
        this.username = username;
        this.personalCollection = new PersonalCollection(new ArrayList<>());
    }

    public ComixUser(String username, int id, PersonalCollection pc) {
        this.id = id;
        this.username = username;
        this.personalCollection = pc;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PersonalCollection getPersonalCollection() {
        return personalCollection;
    }

    @Override
    public String toString() {
        return personalCollection + "," + username + "," + id;
    }

    @Override
    public ComixUser addComic(Comic comic) {
        personalCollection.addComic(comic);
        return this;
    }

    @Override
    public ComixUser removeComic(Comic comic) {
        personalCollection.removeComic(comic);
        return this;
    }

    @Override
    public ComixUser editComic(Comic comic) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What comic do you want to edit (Series Title)?");
        String editComic = scanner.nextLine();
        System.out.println();
        personalCollection.setSearcher(new ExactMatchSearch("Series Titles", editComic));
        Comic com = personalCollection.search().get(0);

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

        personalCollection.editComic(com, publisher, seriesTitle, Integer.parseInt(volumeNum),
                issueNum, publicationDate, creators, "", "", BigDecimal.valueOf(0));
        scanner.close();
        return this;
    }

    @Override
    public ComixUser markComic(Comic comic) {
        personalCollection.markSlabbed(comic);
        return this;
    }

    @Override
    public PersonalCollection getPC() {
        return personalCollection;
    }

    @Override
    public boolean checkProxy(){
        return true;
    }

}
