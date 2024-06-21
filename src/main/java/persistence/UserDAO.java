package persistence;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import model.Comic;
import model.ComixUser;

public interface UserDAO {
    
    List<String[]>  getUsers() throws IOException, CsvException;

    ComixUser getUser(int id) throws IOException, CsvException;

    Collection<ComixUser>  getUsersArray() throws IOException;

    ComixUser getUserWithName(String username);

    ComixUser createUser(String username) throws IOException, CsvException;

    ComixUser addToPersonalCollection(ComixUser user, Comic comic) throws IOException, CsvException;

    ComixUser removeFromPersonalCollection(ComixUser user, Comic comic) throws IOException, CsvException;
}


