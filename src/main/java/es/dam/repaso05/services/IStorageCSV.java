package es.dam.repaso05.services;

import es.dam.repaso05.dto.MagoDTO;

import java.nio.file.Path;
import java.util.List;

public interface IStorageCSV<T> {
    List<T> importarCSV(Path path);

    void exportarCSV(List<T> lista, boolean append, Path path);

    void autosaveCSV(List<T> lista, boolean append);
}
