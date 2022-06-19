package es.dam.repaso05.services;

import es.dam.repaso05.dto.MagoDTO;

import java.nio.file.Path;
import java.util.List;

public interface IStorageJSON<T> {
    List<T> importarJSON(Path path);

    void exportarJSON(List<T> lista, Path path);

    void autosaveJSON(List<T> lista);
}
