package com.clinica.odontologica.service;

import java.util.List;
import java.util.Optional;

import com.clinica.odontologica.model.Estado;
import com.clinica.odontologica.model.Odontologo;
import com.clinica.odontologica.model.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.odontologica.exceptions.ResourceNotFoundException;
import com.clinica.odontologica.exceptions.TurnoConflictException;
import com.clinica.odontologica.model.Turno;
import com.clinica.odontologica.Dto.TurnoDTO;
import com.clinica.odontologica.Repository.TurnoRepository;
import java.time.LocalDate;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    public TurnoDTO guardarTurno(Turno turno) {
        if (turno.getEstado() == null) {
            turno.setEstado(Estado.PROGRAMADO);
        }

        if (turno.getFechaTurno().isBefore(LocalDate.now())) {
            throw new TurnoConflictException("No se puede programar un turno para una fecha pasada");
        }

        if (turnoRepository.existsByOdontologoIdAndFechaTurno(turno.getOdontologo().getId(), turno.getFechaTurno())) {
            throw new TurnoConflictException("El odontologo ya tiene un turno programado para esa fecha");
        }

        Optional<Paciente> paciente = pacienteService.buscarPacienteEntidadPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoEntidadPorId(turno.getOdontologo().getId());

        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + turno.getPaciente().getId());
        }
        if (odontologo.isEmpty()) {
            throw new ResourceNotFoundException("Odontologo no encontrado con ID: " + turno.getOdontologo().getId());
        }

        Turno turnoGuardado = turnoRepository.save(turno);
        return turnoATurnoDTO(turnoGuardado);
    }

    public Optional<Turno> buscarTurnoPorId(Integer id) {
        return Optional.of(turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id)));
    }

    public void eliminarTurno(Integer id) {
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, turno no encontrado con ID: " + id);
        }
        turnoRepository.deleteById(id);
    }

    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    public TurnoDTO actualizarTurno(TurnoDTO turnoDTO) {
        Turno turno = turnoRepository.findById(turnoDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + turnoDTO.getId()));

        if (turnoDTO.getFechaTurno().isBefore(LocalDate.now())) {
            throw new TurnoConflictException("No se puede actualizar un turno a una fecha pasada");
        }

        Optional<Paciente> paciente = pacienteService.buscarPacienteEntidadPorId(turnoDTO.getIdPaciente());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoEntidadPorId(turnoDTO.getIdOdontologo());

        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + turnoDTO.getIdPaciente());
        }
        if (odontologo.isEmpty()) {
            throw new ResourceNotFoundException("Odontologo no encontrado con ID: " + turnoDTO.getIdOdontologo());
        }

        turno.setPaciente(paciente.get());
        turno.setOdontologo(odontologo.get());
        turno.setFechaTurno(turnoDTO.getFechaTurno());
        turno.setObservaciones(turnoDTO.getObservaciones());
        if (turnoDTO.getEstado() != null) {
            turno.setEstado(Estado.valueOf(turnoDTO.getEstado().toUpperCase()));
        }

        Turno turnoActualizado = turnoRepository.save(turno);
        return turnoATurnoDTO(turnoActualizado);
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setIdPaciente(turno.getPaciente().getId());
        turnoDTO.setIdOdontologo(turno.getOdontologo().getId());
        turnoDTO.setFechaTurno(turno.getFechaTurno());
        turnoDTO.setObservaciones(turno.getObservaciones());
        if (turno.getEstado() != null) {
            turnoDTO.setEstado(turno.getEstado().name());
        }
        return turnoDTO;
    }
}
