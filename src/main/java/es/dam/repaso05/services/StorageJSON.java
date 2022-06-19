package es.dam.repaso05.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.dam.repaso05.dto.MagoDTO;
import es.dam.repaso05.utils.LocalDateAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StorageJSON implements IStorageJSON<MagoDTO> {
    public static StorageJSON instance;

    private final String jsonFile =  System.getProperty("user.dir") + File.separator + "data" + File.separator + "json" + File.separator + "hogwarts.json";

    //Singleton
    public static StorageJSON getInstance() {
        if (instance == null) {
            instance = new StorageJSON();
        }
        return instance;
    }

    private StorageJSON() {
    }

    @Override
    public List<MagoDTO> importarJSON(Path path) {
        //TODO Método de importar un archivo json obtenido de un Filechooser.
        return null;
    }

    @Override
    public void autosaveJSON(List<MagoDTO> magos) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        PrintWriter f = null;

        try {
            f = new PrintWriter(new File(jsonFile));
            f.println(gson.toJson(magos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            if (f != null) {
                f.close();
            }
        }
    }

    @Override
    public void exportarJSON(List<MagoDTO> magos, Path path) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        PrintWriter f = null;

        try {
            f = new PrintWriter(String.valueOf(path));
            f.println(gson.toJson(magos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            if (f != null) {
                f.close();
            }
        }
    }
}
