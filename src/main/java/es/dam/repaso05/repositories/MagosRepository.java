package es.dam.repaso05.repositories;

import es.dam.repaso05.dto.MagoDTO;
import es.dam.repaso05.managers.DataBaseManager;
import es.dam.repaso05.models.Mago;
import es.dam.repaso05.services.StorageCSV;
import es.dam.repaso05.services.StorageJSON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MagosRepository implements IMagosRepository {
    public static MagosRepository instance;

    ObservableList<Mago> repository = FXCollections.observableArrayList();

    DataBaseManager db = DataBaseManager.getInstance();

    StorageJSON storageJSON = StorageJSON.getInstance();

    StorageCSV storageCSV = StorageCSV.getInstance();

    //Singleton
    public static MagosRepository getInstance() {
        if(instance == null) {
            instance = new MagosRepository();
        }
        return instance;
    }

    private MagosRepository() {
    }

    @Override
    public ObservableList<Mago> findAll() throws SQLException {
        String sql = "SELECT * FROM HOGWARTS";
        db.open();
        var res = db.select(sql).orElseThrow(() -> new SQLException("Error al compilar los magos."));
        repository.clear();
        while(res.next()) {
            repository.add(
                    new Mago(
                          res.getInt("id"),
                          res.getString("nombre"),
                            res.getString("apodo"),
                            LocalDate.parse(res.getString("fNacimiento")),
                            res.getString("casa"),
                            res.getInt("altura"),
                            res.getString("hechizo")
                    )
            );
        }
        db.close();

        if(repository.isEmpty()) {
            System.out.println("La lista de magos está vacía.");
        }

        return repository;
    }

    @Override
    public void save(Mago mago) throws SQLException {
        String sql = "INSERT INTO HOGWARTS(nombre, apodo, fNacimiento, casa, altura, hechizo) VALUES(?, ?, ?, ?, ?, ?)";
        db.open();
        var res = db.insert(sql, mago.getNombre(), mago.getApodo(), mago.getfNacimiento(), mago.getCasa(), mago.getAltura(), mago.getHechizo());
        db.close();
    }

    @Override
    public void update(Mago mago) throws SQLException {
        String sql = "UPDATE HOGWARTS SET nombre = ?, apodo = ?, fNacimiento = ?, casa = ?, altura = ?, hechizo = ? WHERE id = ?";
        db.open();
        var res = db.update(sql, mago.getNombre(), mago.getApodo(), mago.getfNacimiento(), mago.getCasa(), mago.getAltura(), mago.getHechizo(), mago.getId());
        db.close();

        repository.set(repository.indexOf(mago), mago);
    }

    @Override
    public void delete(Mago mago) throws SQLException {
        String sql = "DELETE FROM HOGWARTS WHERE id = ?";
        db.open();
        db.delete(sql, mago.getId());
        db.close();

        repository.remove(mago);
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM HOGWARTS";
        db.open();
        db.delete(sql);
        db.close();

        repository.remove(0, repository.size() - 1);
    }

    @Override
    public void restoreJSON(Path path) throws SQLException {

    }

    @Override
    public void restoreCSV(Path path) throws SQLException {
        List<MagoDTO> magosDTO = storageCSV.importarCSV(path);
        repository.clear();
        String sql = "DELETE FROM HOGWARTS";
        db.open();
        db.update(sql);
        db.close();

        magosDTO.forEach(m -> {
            try {
                save(m.fromDTO());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void autosaveCSV() {
        List<MagoDTO> magosDTO = repository.stream().map(MagoDTO::new).toList();
        storageCSV.autosaveCSV(magosDTO, false);
    }

    @Override
    public void autosaveJSON() {
        List<MagoDTO> magosDTO = repository.stream().map(MagoDTO::new).toList();
        storageJSON.autosaveJSON(magosDTO);
    }

    @Override
    public void backupCSV(Path path) {
        List<MagoDTO> magosDTO = repository.stream().map(MagoDTO::new).toList();
        storageCSV.exportarCSV(magosDTO, false, path);
    }

    @Override
    public void backupJSON(Path path) {
        List<MagoDTO> magosDTO = repository.stream().map(MagoDTO::new).toList();
        storageJSON.exportarJSON(magosDTO, path);
    }

    @Override
    public List<Mago> scan() throws SQLException {
        List<Mago> magos = new ArrayList<>();
        String sql = "SELECT * FROM HOGWARTS";
        db.open();
        var res = db.select(sql).orElseThrow(() -> new SQLException("Error al compilar los magos."));
        while(res.next()) {
            magos.add(
                    new Mago(
                            res.getInt("id"),
                            res.getString("nombre"),
                            res.getString("apodo"),
                            LocalDate.parse(res.getString("fNacimiento")),
                            res.getString("casa"),
                            res.getInt("altura"),
                            res.getString("hechizo")
                    )
            );
        }
        db.close();

        if(magos.isEmpty()) {
            System.out.println("La lista de magos está vacía.");
        }

        return magos;
    }
}
