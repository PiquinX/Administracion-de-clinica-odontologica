package com.clinica.odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.clinica.odontologica.model.Domicilio;

public class DomicilioDAOH2 implements iDao<Domicilio> {
    private final static String SQL_INSERT = "INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES (?, ?, ?, ?)";

    @Override
    public Domicilio guardar(Domicilio object) {
        try {
            Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, object.getCalle());
            ps.setInt(2, object.getNumero());
            ps.setString(3, object.getLocalidad());
            ps.setString(4, object.getProvincia());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                object.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public Domicilio buscar(Integer id) {
        return null;
    }

    @Override
    public void actualizar(Domicilio object) {

    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public List<Domicilio> listar() {
        return null;
    }
}
