package es.dam.repaso05.repositories;

import es.dam.repaso05.models.Mago;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public interface IMagosRepository extends ICRUDRepository<Mago> {

    void restoreCSV(Path path) throws SQLException;

    void autosaveCSV();

    void autosaveJSON();

    void backupCSV(Path path);

    void backupJSON(Path path);

    List<Mago> scan() throws SQLException;
}
