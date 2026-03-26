package com.clinica.odontologica.service;

import java.util.List;
import java.util.Optional;

import com.clinica.odontologica.model.Odontologo;
import com.clinica.odontologica.model.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.odontologica.model.Turno;
import com.clinica.odontologica.Dto.TurnoDTO;
import com.clinica.odontologica.Repository.TurnoRepository;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    public TurnoDTO guardarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if (paciente.isPresent() && odontologo.isPresent()) {
            Turno turnoGuardado = turnoRepository.save(turno);
            return turnoATurnoDTO(turnoGuardado);
        } else {
            throw new RuntimeException("Paciente u Odontologo no encontrado");
        }
    }

    public Optional<Turno> buscarTurnoPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }

    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    public TurnoDTO actualizarTurno(TurnoDTO turnoDTO) {
        Turno turno = new Turno();

        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turnoDTO.getIdPaciente());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turnoDTO.getIdOdontologo());

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setId(turnoDTO.getId());
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFechaTurno(turnoDTO.getFechaTurno());

            Turno turnoActualizado = turnoRepository.save(turno);
            return turnoATurnoDTO(turnoActualizado);
        } else {
            throw new RuntimeException("Paciente u Odontologo no encontrado");
        }
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setIdPaciente(turno.getPaciente().getId());
        turnoDTO.setIdOdontologo(turno.getOdontologo().getId());
        turnoDTO.setFechaTurno(turno.getFechaTurno());
        return turnoDTO;
    }
}
