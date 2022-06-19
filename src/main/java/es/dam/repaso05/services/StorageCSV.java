package es.dam.repaso05.services;

import es.dam.repaso05.dto.MagoDTO;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StorageCSV implements IStorageCSV<MagoDTO> {
    public static StorageCSV instance;

    private final String csvFile =  System.getProperty("user.dir") + File.separator + "data" + File.separator + "csv" + File.separator + "hogwarts.csv";

    //Singleton
    public static StorageCSV getInstance() {
        if (instance == null) {
            instance = new StorageCSV();
        }
        return instance;
    }

    private StorageCSV() {
    }

    @Override
    public List<MagoDTO> importarCSV(Path path) {
        File fichero = null;
        BufferedReader f = null;
        List<MagoDTO> magos = new ArrayList<>();

        try {
            fichero = new File(String.valueOf(path));
            f = new BufferedReader(new FileReader(fichero));

            String linea;
            while((linea = f.readLine()) != null) {
                magos.add(getMagos(linea));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return magos;
    }

    private MagoDTO getMagos(String linea) {
        String[] campos = linea.split(",");
        int id = Integer.parseInt(campos[0]);
        String nombre = campos[1];
        String apodo = campos[2];
        LocalDate fNacimiento = LocalDate.parse(campos[3]);
        String casa = campos[4];
        int altura = Integer.parseInt(campos[5]);
        String hechizo = campos[6];

        return new MagoDTO(id, nombre, apodo, fNacimiento, casa, altura, hechizo);
    }

    @Override
    public void autosaveCSV(List<MagoDTO> magos, boolean append) {
        File fichero = null;
        PrintWriter f = null;

        try {
            fichero = new File(csvFile);
            f = new PrintWriter(new File(csvFile));

            for(MagoDTO m: magos) {
                f.println(m.toFile());
            }

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
    public void exportarCSV(List<MagoDTO> magos, boolean append, Path path) {
        File fichero = null;
        PrintWriter f = null;

        try {
            fichero = new File(String.valueOf(path));
            f = new PrintWriter(new File(String.valueOf(path)));

            for(MagoDTO m: magos) {
                f.println(m.toFile());
            }

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
