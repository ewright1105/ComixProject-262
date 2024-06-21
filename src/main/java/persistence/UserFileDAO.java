package persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import model.Comic;
import model.PersonalCollection;
import model.ComixUser;

public class UserFileDAO implements UserDAO {
    Map<Integer, ComixUser> users;
    private CSVWriter writer;
    private static int nextId;
    private String filename;
    private CSVReader reader;

    public UserFileDAO(String filename) throws IOException, CsvException {
        this.filename = filename;
        this.reader = new CSVReader(new FileReader(filename));
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException, CsvException {

        List<String[]> allUsers = new ArrayList<>();
        for (Map.Entry<Integer, ComixUser> pair : users.entrySet()) {
            String[] user = new String[3];
            user[2] = String.valueOf(pair.getValue().getId());
            user[1] = pair.getValue().getUsername();
            user[0] = pair.getValue().getPersonalCollection().toString();
            allUsers.add(user);

        }
        FileWriter fw = new FileWriter(filename, false);
        
        writer = new CSVWriter(new FileWriter(filename, true));
        writer.writeAll(allUsers);
        fw.close();
        writer.close();
        return true;
    }

    private boolean load() throws IOException, CsvException {
        users = new TreeMap<>();
        nextId = 0;
        List<String[]> userArray = reader.readAll();
        reader.close();
    
        for (String[] user : userArray) {
            String[] pcCSV = user[0].split("[.]");
            ArrayList<Comic> pc = new ArrayList<>();
            for (String com : pcCSV) {
                String[] comics = com.split(",");
                if (comics.length >= 6) {
                    pc.add(new Comic(comics[3], comics[0], Integer.parseInt(comics[1]), comics[2],
                            comics[4] + "," + comics[5].substring(0, Math.min(5, comics[5].length()))));
                }
            }
            users.put(Integer.parseInt(user[2]),
                    new ComixUser(user[1], Integer.parseInt(user[2]), new PersonalCollection(pc)));
            if (Integer.parseInt(user[2]) > nextId)
                nextId = Integer.parseInt(user[2]);
        }
        ++nextId;
        return true;
    }    

    public ComixUser getUserWithName(String username) {
        for (ComixUser user : users.values()) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<String[]> getUsers() throws IOException, CsvException {
        List<String[]> vals = reader.readAll();
        reader.close();
        return vals;
    }

    @Override
    public ComixUser getUser(int id) throws IOException, CsvException {
        return users.get(id);
    }

    @Override
    public Collection<ComixUser> getUsersArray() throws IOException {
        return users.values();
    }

    @Override
    public ComixUser createUser(String username) throws IOException, CsvException {
        synchronized (users) {
            for (Map.Entry<Integer, ComixUser> pair : users.entrySet()) {
                if (pair.getValue().getUsername().equals(username)) {
                    return null;
                }
            }

            ComixUser newUser = new ComixUser(username, nextId());
            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    @Override
    public ComixUser addToPersonalCollection(ComixUser user, Comic comic) throws IOException, CsvException {
        for (ComixUser person : users.values()) {
            if (person.getUsername().equals(user.getUsername())) {
                user.getPersonalCollection().addComic(comic);
            }
        }
        save();
        return user;
    }

    @Override
    public ComixUser removeFromPersonalCollection(ComixUser user, Comic comic) throws IOException, CsvException {
        for (ComixUser person : users.values()) {
            if (person.getUsername().equals(user.getUsername())) {
                user.getPersonalCollection().removeComic(comic);
            }
        }
        save();
        return user;
    }
}
