package es.dam.repaso05.services;

import es.dam.repaso05.dto.MagoDTO;

import java.nio.file.Path;
import java.util.List;

public interface IStorageJSON<T> {

    void exportarJSON(List<T> lista, Path path);

    List<MagoDTO> importarJSON(Path path);

    void autosaveJSON(List<T> lista);
}
