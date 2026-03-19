package com.clinica.odontologica.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private static final String SQL_DROP_CREATE_PACIENTES = """
            DROP TABLE IF EXISTS PACIENTES;
            CREATE TABLE PACIENTES (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                NOMBRE VARCHAR(100) NOT NULL,
                APELLIDO VARCHAR(100) NOT NULL,
                CEDULA VARCHAR(20) NOT NULL,
                FECHA_INGRESO DATE NOT NULL,
                DOMICILIO_ID INT NOT NULL
            );
            """;

    private static final String SQL_DROP_CREATE_DOMICILIOS = """
            DROP TABLE IF EXISTS DOMICILIOS;
            CREATE TABLE DOMICILIOS (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                CALLE VARCHAR(100) NOT NULL,
                NUMERO INT NOT NULL,
                LOCALIDAD VARCHAR(100) NOT NULL,
                PROVINCIA VARCHAR(100) NOT NULL
            );
            """;

    private static final String SQL_Prueba = """
            INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('Calle Falsa', 123, 'Springfield', 'Buenos Aires');
            INSERT INTO PACIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID) VALUES ('Juan', 'Perez', '12345678', '2022-01-01', 1);
            """;

    public static void crearTablas() {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.createStatement().execute(SQL_DROP_CREATE_DOMICILIOS);
            connection.createStatement().execute(SQL_DROP_CREATE_PACIENTES);
            connection.createStatement().execute(SQL_Prueba);
            System.out.println("Tablas creadas exitosamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                // e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        // mem es para que la base de datos se cree en memoria RAM
        // return DriverManager.getConnection("jdbc:h2:mem:~/clinicaOdontologicaTest",
        // "sa", "sa");

        // para que la base de datos se guarde en disco
        return DriverManager.getConnection("jdbc:h2:~/clinicaOdontologicaTest", "sa", "sa");
    }
}
