package model;

import java.io.File;
import java.util.Collection;

public interface DatabaseAdapter {
    public File export(Collection<String[]> data);
    public Collection<String[]> importFile(File file);
}
