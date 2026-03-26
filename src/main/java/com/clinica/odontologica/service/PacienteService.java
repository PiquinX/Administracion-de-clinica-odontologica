package com.clinica.odontologica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.odontologica.Repository.PacienteRepository;
import com.clinica.odontologica.model.Paciente;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }
    // private iDao<Paciente> pacienteiDao;

    // private iDao<Domicilio> domicilioiDao;

    // public PacienteService() {
    // pacienteiDao = new PacienteDAOH2();
    // domicilioiDao = new DomicilioDAOH2();
    // }

    // public Paciente guardarPaciente(Paciente paciente) {
    // if (paciente.getDomicilio() != null && paciente.getDomicilio().getId() ==
    // null) {
    // domicilioiDao.guardar(paciente.getDomicilio());
    // }
    // return pacienteiDao.guardar(paciente);
    // }

    // public Paciente buscarPaciente(Integer id) {
    // return pacienteiDao.buscar(id);
    // }

    // public void actualizarPaciente(Paciente paciente) {
    // pacienteiDao.actualizar(paciente);
    // }

    // public void eliminarPaciente(Integer id) {
    // pacienteiDao.eliminar(id);
    // }

    // public List<Paciente> listarPacientes() {
    // return pacienteiDao.listar();
    // }
}
