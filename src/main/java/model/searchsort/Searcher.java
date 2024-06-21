package model.searchsort;

import java.util.stream.Stream;

public enum Searcher {
    TITLE("Series Titles", 0),
    STORYTITLE("Story Titles", 1),
    ISSUE("Issue Numbers", 2),
    PUBLISHER("Publishers", 3),
    DATE("Publish Dates", 4),
    CREATORS("Creators", 5),
    OTHER("Other", 6);

    public final Integer id;
    public final String search;

    public Integer getId() {
        return id;
    }

    public String getSearch() {
        return search;
    }

    private Searcher(String search, int id) {
        this.id = id;
        this.search = search;
    }

    public static Stream<Searcher> stream() {
        return Stream.of(Searcher.values());
    }
}