package com.clinica.odontologica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.odontologica.Dto.PacienteRequestDTO;
import com.clinica.odontologica.Dto.PacienteResponseDTO;
import com.clinica.odontologica.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@Valid @RequestBody PacienteRequestDTO dto) {

        PacienteResponseDTO pacienteResponseDTO = pacienteService.registrarPaciente(dto);
        return new ResponseEntity<>(pacienteResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPaciente(@PathVariable Integer id) {
        Optional<PacienteResponseDTO> paciente = pacienteService.buscarPacientePorId(id);

        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(paciente.get(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPaciente(@PathVariable Integer id, @Valid @RequestBody PacienteRequestDTO dto) {
        try {
            return ResponseEntity.ok(pacienteService.actualizarPaciente(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }
}
