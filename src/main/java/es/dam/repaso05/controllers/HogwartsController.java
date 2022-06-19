package es.dam.repaso05.controllers;

import es.dam.repaso05.dto.MagoDTO;
import es.dam.repaso05.models.Mago;
import es.dam.repaso05.repositories.MagosRepository;
import es.dam.repaso05.services.StorageJSON;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HogwartsController {
    MagosRepository magosRepository = MagosRepository.getInstance();

    StorageJSON storageJSON = StorageJSON.getInstance();

    Mago[][] matriz = new Mago[5][5];

    private List<Mago> magoList;

    @FXML
    public Button initButton;
    @FXML
    public MenuItem menuCerrar;
    @FXML
    public MenuItem menuCSVCargar;
    @FXML
    public MenuItem menuJSONCargar;
    @FXML
    public MenuItem menuCSVExportar;
    @FXML
    public MenuItem menuJSONExportar;
    @FXML
    public MenuItem menuInforme;
    @FXML
    public TableView<Mago> magosTable;
    @FXML
    public TableColumn<Mago, String> nombreColumn;
    @FXML
    public TableColumn<Mago, String> casaColumn;
    @FXML
    public TextField nombreField;
    @FXML
    public TextField apodoField;
    @FXML
    public DatePicker nacimientoPicker;
    @FXML
    public ComboBox<String> casaComboBox;
    @FXML
    public TextField alturaField;
    @FXML
    public TextField hechizoField;
    @FXML
    public TextArea tablero;
    @FXML
    public Button saveButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;

    public void initialize() {
        try {
            magosTable.setItems(magosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        casaComboBox.getItems().addAll("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin");

        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        casaColumn.setCellValueFactory(cellData -> cellData.getValue().casaProperty());

        magosTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onMagoSeleted(newValue)
        );

        setVacio();
    }

    private void setVacio() {
        nombreField.setText("");
        apodoField.setText("");
        nacimientoPicker.setValue(LocalDate.parse("2000-01-01"));
        casaComboBox.setValue("");
        alturaField.setText("");
        hechizoField.setText("");
    }

    private void onMagoSeleted(Mago mago) {
        if (mago != null) {
            setData(mago);
        } else {
            setVacio();
        }
    }

    private void setData(Mago mago) {
        nombreField.setText(mago.getNombre());
        apodoField.setText(mago.getApodo());
        nacimientoPicker.setValue(mago.getfNacimiento());
        casaComboBox.setValue(mago.getCasa());
        alturaField.setText(String.valueOf(mago.getAltura()));
        hechizoField.setText(mago.getHechizo());
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Saliendo...");
        alert.setContentText("¿Está seguro de que desea salir?");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK) {
            magosRepository.autosaveCSV();
            magosRepository.autosaveJSON();
            Platform.exit();
        } else {
            alert.close();
        }
    }

    public void onActionCargarCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un archivo .csv:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.csv"));
        Path path = fileChooser.showOpenDialog(nombreField.getScene().getWindow()).toPath();

        try {
            magosRepository.restoreCSV(path);
            magosTable.setItems(magosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onActionCargarJSON(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un archivo .json:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.json"));
        Path path = fileChooser.showOpenDialog(nombreField.getScene().getWindow()).toPath();

        try {
            var lista = storageJSON.importarJSON(path);
            if (lista.size() > 0) {
                magosRepository.deleteAll();
                for (MagoDTO mago : lista) {
                    magosRepository.save(mago.fromDTO());
                }
                System.out.println("Datos importados con éxito al repositorio: " + lista.size() + " magos");
            } else {
                System.out.println("Ha existido un problema al importar los datos");
            }
            magosTable.setItems(magosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionExportarCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un archivo .csv:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.csv"));
        Path path = fileChooser.showOpenDialog(nombreField.getScene().getWindow()).toPath();

        magosRepository.backupCSV(path);
    }

    public void onActionExportarJSON(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un archivo .json:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.json"));
        Path path = fileChooser.showOpenDialog(nombreField.getScene().getWindow()).toPath();

        magosRepository.backupJSON(path);
    }

    public void onActionCrearTXT(ActionEvent actionEvent) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(System.getProperty("user.dir") + File.separator + "data" + File.separator + "txt" + File.separator + "informe.txt");
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DoubleSummaryStatistics stats = null;
        String res1 = "";
        String res2 = "";
        String res3 = "";
        String res4 = "";
        String res5 = "";

        try {
            stats = magosRepository.findAll().stream().mapToDouble(Mago::getAltura).summaryStatistics();
            res1 = magosRepository.findAll().stream().collect(Collectors.groupingBy(Mago::getCasa)).toString();
            res2 = magosRepository.findAll().stream().collect(Collectors.groupingBy(Mago::getCasa, Collectors.counting())).toString();
            res3 = magosRepository.findAll().stream().filter(m -> m.getNombre().contains("Potter")).toList().toString();
            res4 = magosRepository.findAll().stream().max(Comparator.comparing(Mago::getfNacimiento)).get().getNombre();
            res5 = magosRepository.findAll().stream().min(Comparator.comparing(Mago::getfNacimiento)).get().getNombre();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert stats != null;
        String informe = "INFORME DE MAGOS: " + "\n"
                + "-------------------------------------" + "\n"
                + "Altura más alta: " + stats.getMax() + "\n"
                + "Altura más baja: " + stats.getMin() + "\n"
                + "Altura media: " + stats.getAverage() + "\n"
                + "-------------------------------------" + "\n"
                + "Magos ordenados por casa: " + res1 + "\n"
                + "Cantidad de magos en cada casa: " + res2 + "\n"
                + "-------------------------------------" + "\n"
                + "Mago cuyo apellido es Potter: " + res3 + "\n"
                + "-------------------------------------" + "\n"
                + "Mago más joven: " + res4 + "\n"
                + "-------------------------------------" + "\n"
                + "Mago más viejo: " + res5 + "\n";

        try {
            assert oos != null;
            oos.writeBytes(informe);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCrearAction(ActionEvent actionEvent) {
        Mago mago = new Mago(nombreField.getText(), apodoField.getText(), nacimientoPicker.getValue(), casaComboBox.getValue(), Integer.parseInt(alturaField.getText()), hechizoField.getText());

        try {
            magosRepository.save(mago);
            magosTable.setItems(magosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateAction(ActionEvent actionEvent) {
        Mago mago = magosTable.getSelectionModel().getSelectedItem();

        mago.setNombre(nombreField.getText());
        mago.setApodo(apodoField.getText());
        mago.setfNacimiento(nacimientoPicker.getValue());
        mago.setCasa(casaComboBox.getValue());
        mago.setAltura(Integer.parseInt(alturaField.getText()));
        mago.setHechizo(hechizoField.getText());

        try {
            magosRepository.update(mago);
            magosTable.setItems(magosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteAction(ActionEvent actionEvent) {
        Mago mago = magosTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText("Eliminando a " + mago.getNombre());
        alert.setContentText("¿Está seguro de que desea eliminar al mago?");
        Optional<ButtonType> button = alert.showAndWait();
        if(button.get() == ButtonType.OK) {
            try {
                magosRepository.delete(mago);
                magosTable.setItems(magosRepository.findAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            alert.close();
        }
    }

    public void onActionIniciarMatriz(ActionEvent actionEvent) {
        try {
            magoList = magosRepository.scan();
            initMagos();
            printMatrizTextArea();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tablero.setEditable(false);
        initButton.setDisable(true);
    }

    public void printMatrizTextArea() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == null) {
                    tablero.appendText("[X] ");
                } else {
                    tablero.appendText("[M] ");
                }
            }
            tablero.appendText("\n");
        }
    }

    public void printMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == null) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("🧙");
                }
            }
            System.out.println();
        }
    }

    public void initMagos() {
        int magosCount = 0;
        int magoRandom;
        int fila;
        int columna;

        while (magosCount <= 4) {
            magoRandom = (int) (Math.random() * (magoList.size() -1));
            fila = (int) (Math.random() * 4);
            columna = (int) (Math.random() * 4);

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if (fila == i && columna == j) {
                        if (matriz[i][j] == null) {
                            matriz[i][j] = magoList.remove(magoRandom);

                            magosCount++;
                        }
                    }
                }
            }
            printMatriz();
            System.out.println("------------------------------");
        }
    }
}