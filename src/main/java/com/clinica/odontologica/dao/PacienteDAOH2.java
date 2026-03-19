package com.clinica.odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.sql.Date;

import com.clinica.odontologica.model.Paciente;

public class PacienteDAOH2 implements iDao<Paciente> {
    private final static String SQL_INSERT = "INSERT INTO PACIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_SELECT = "SELECT * FROM PACIENTES WHERE ID = ?";
    private final String SQL_UPDATE = "UPDATE PACIENTES SET NOMBRE = ?, APELLIDO = ?, CEDULA = ?, FECHA_INGRESO = ?, DOMICILIO_ID = ? WHERE ID = ?";
    private final String SQL_DELETE = "DELETE FROM PACIENTES WHERE ID = ?";
    private final String SQL_SELECT_ALL = "SELECT * FROM PACIENTES";

    // from log4j
    private static final Logger logger = LoggerFactory.getLogger(PacienteDAOH2.class);

    @Override
    public Paciente guardar(Paciente paciente) {
        logger.info("Guardando paciente: " + paciente);
        try {
            Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getCedula());
            ps.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps.setInt(5, paciente.getDomicilio().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                paciente.setId(rs.getInt(1));
                logger.info("Paciente guardado con ID: " + paciente.getId());
            }
        } catch (Exception e) {
            logger.error("Error al guardar el paciente: " + e.getMessage());
        }

        return paciente;
    }

    @Override
    public Paciente buscar(Integer id) {
        return null;
    }

    @Override
    public void actualizar(Paciente object) {

    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public List<Paciente> listar() {
        return null;
    }
}
