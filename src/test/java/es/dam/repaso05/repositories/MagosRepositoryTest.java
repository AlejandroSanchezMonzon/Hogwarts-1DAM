package es.dam.repaso05.repositories;

import es.dam.repaso05.models.Mago;
import es.dam.repaso05.repositories.MagosRepository;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MagosRepositoryTest {
    //TODO Hacer los test arreglando el problema del ID Autoincrementable y la conexión con la Base de Datos real.
    MagosRepository magosRepository = MagosRepository.getInstance();

    Mago magoTest = new Mago("Alejandro", "Alex", LocalDate.parse("2002-10-23"), "Gryffindor", 180,"Escupir");

    @AfterAll
    static void setUpAll() throws SQLException {

    }

    @BeforeEach
    void setUp() throws SQLException {
        magosRepository.deleteAll();
    }

    @Test
    void findAll() throws SQLException {
        magosRepository.save(magoTest);
        var res = magosRepository.findAll();
        var resMago = magosRepository.findAll().get(0);

        assertAll(
                () -> assertTrue(res.contains(resMago)),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(resMago, res.stream().filter(r -> r.getNombre().equals(magoTest.getNombre())).toList().get(0))
        );
    }

    @Test
    void save() throws SQLException {
        magosRepository.save(magoTest);
        var res = magosRepository.findAll();
        var resMago = res.get(0);

        assertAll(
                () -> assertEquals(magoTest, resMago),
                () -> assertEquals(magoTest.getNombre(), resMago.getNombre()),
                () -> assertEquals(magoTest.getfNacimiento(), resMago.getfNacimiento()),
                () -> assertEquals(magoTest.getCasa(), resMago.getCasa())
        );
    }


    @Test
    void update() throws SQLException {
        magosRepository.save(magoTest);
        magoTest.setNombre("Mireya");
        magoTest.setCasa("Slytherin");
        magoTest.setAltura(164);

        magosRepository.update(magoTest);
        var res =magosRepository.findAll().get(0);

        assertAll(
                () -> assertEquals(magoTest, res),
                () -> assertEquals(magoTest.getNombre(), res.getNombre()),
                () -> assertEquals(magoTest.getfNacimiento(), res.getfNacimiento()),
                () -> assertEquals(magoTest.getCasa(), res.getCasa()),
                () -> assertEquals(magoTest.getAltura(), res.getAltura())
        );
    }

    @Test
    void delete() throws SQLException {
        magosRepository.save(magoTest);
        var res = magosRepository.findAll();

        magosRepository.delete(magoTest);
        var resVacio = magosRepository.findAll();

        assertAll(
                () -> assertEquals(1, res.size()),
                () -> assertEquals(0, resVacio.size())
        );
    }
}