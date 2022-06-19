package es.dam.repaso05.repositories;

import es.dam.repaso05.models.Mago;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MagosRepositoryTest {
    MagosRepository magosRepository = MagosRepository.getInstance();

    Mago magoTest = new Mago( "Alejandro", "Alex", LocalDate.parse("2002-10-23"), "Gryffindor", 180,"Escupir");

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
                //() -> assertTrue(res.contains(resMago)),
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
        magosRepository.save(new Mago("Mireya Sanchez", "Mireya", LocalDate.parse("2002-07-26"), "Gryffindor", 164,"Escupir"));
        var res1 =magosRepository.findAll().get(0);

        magoTest.setNombre("Mireya");
        magoTest.setCasa("Slytherin");
        magoTest.setAltura(164);

        magosRepository.update(magoTest);
        var res2 =magosRepository.findAll().get(0);

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
        var res = magosRepository.findAll().size();

        magosRepository.delete(magoTest);
        var resVacio = magosRepository.findAll().size();

        assertAll(
                () -> assertEquals(0, resVacio),
                () -> assertEquals(1, res)
        );
    }
}