package com.clinica.odontologica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.odontologica.model.Paciente;
import com.clinica.odontologica.service.PacienteService;

//This allows us to interact with the view and the service
@RestController
@RequestMapping("/api-paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    // CRUD - to send data to the service
    @PostMapping
    public Paciente registrarPaciente(@RequestBody Paciente paciente) {
        // here we can filter or add small defensive mechanisms
        return pacienteService.registrarPaciente(paciente);
    }

    @GetMapping("/{id}")
    public Paciente buscarPaciente(@PathVariable Integer id) {
        return pacienteService.buscarPacientePorId(id);
    }

    @PutMapping
    public void actualizarPaciente(Paciente paciente) {
        pacienteService.actualizarPaciente(paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }
}
