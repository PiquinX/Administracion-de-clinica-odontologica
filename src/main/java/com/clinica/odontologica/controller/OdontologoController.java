package com.clinica.odontologica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.clinica.odontologica.Dto.OdontologoRequestDTO;
import com.clinica.odontologica.Dto.OdontologoResponseDTO;
import com.clinica.odontologica.service.OdontologoService;

@RestController
@RequestMapping("/api-odontologo")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<OdontologoResponseDTO> registrarOdontologo(@Valid @RequestBody OdontologoRequestDTO dto) {
        return ResponseEntity.ok(odontologoService.registrarOdontologo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoResponseDTO> buscarOdontologoPorId(@PathVariable Integer id) {
        Optional<OdontologoResponseDTO> odontologo = odontologoService.buscarOdontologoPorId(id);
        return odontologo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOdontologo(@PathVariable Integer id, @Valid @RequestBody OdontologoRequestDTO dto) {
        try {
            return ResponseEntity.ok(odontologoService.actualizarOdontologo(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOdontologo(@PathVariable Integer id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OdontologoResponseDTO>> listarOdontologos() {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }
}
