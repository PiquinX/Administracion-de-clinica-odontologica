package com.clinica.odontologica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clinica.odontologica.model.Turno;
import com.clinica.odontologica.Dto.TurnoDTO;
import com.clinica.odontologica.service.TurnoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<?> guardarTurno(@RequestBody Turno turno) {
        try {
            TurnoDTO turnoGuardado = turnoService.guardarTurno(turno);
            return ResponseEntity.ok(turnoGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id) {
        Optional<Turno> turno = turnoService.buscarTurnoPorId(id);
        if (turno.isPresent()) {
            return ResponseEntity.ok(turno.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody TurnoDTO turno) {
        try {
            TurnoDTO turnoActualizado = turnoService.actualizarTurno(turno);
            return ResponseEntity.ok(turnoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
