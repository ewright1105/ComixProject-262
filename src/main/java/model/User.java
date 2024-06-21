package model;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

public interface User {
    ComixUser addComic(Comic comic) throws IOException, CsvException;

    ComixUser removeComic(Comic comic) throws IOException, CsvException;

    ComixUser editComic(Comic comic);

    ComixUser markComic(Comic comic);

    PersonalCollection getPC();

    boolean checkProxy();

    String getUsername();
}
