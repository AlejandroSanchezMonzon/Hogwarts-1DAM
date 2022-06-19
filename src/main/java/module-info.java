/*
@author Alejandro Sánchez Monzón | 1ºDAM
Guthub: AlejandroSanchezMonon
Correo: alejandro.sanchez@gmail.com | alejandrosanchezmonzon1@gmail.com
*/

module es.dam.repaso05 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    //requires org.apache.logging.log4j;
    requires java.sql;
    requires lombok;


    exports es.dam.repaso05;
    opens es.dam.repaso05 to javafx.fxml;

    exports es.dam.repaso05.controllers;
    opens es.dam.repaso05.controllers to javafx.fxml;

    exports es.dam.repaso05.managers;
    opens es.dam.repaso05.managers to javafx.fxml;

    exports es.dam.repaso05.utils;
    opens es.dam.repaso05.utils to javafx.fxml;

    opens es.dam.repaso05.models to com.google.gson;

    opens es.dam.repaso05.dto to com.google.gson;
}