package com.clinica.odontologica.service;

import com.clinica.odontologica.mapper.EntityDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.odontologica.Dto.DomicilioRequestDTO;
import com.clinica.odontologica.Dto.DomicilioResponseDTO;
import com.clinica.odontologica.Dto.PacienteRequestDTO;
import com.clinica.odontologica.Dto.PacienteResponseDTO;
import com.clinica.odontologica.Repository.PacienteRepository;
import com.clinica.odontologica.exceptions.DuplicateResourceException;
import com.clinica.odontologica.exceptions.ResourceNotFoundException;
import com.clinica.odontologica.model.Domicilio;
import com.clinica.odontologica.model.Paciente;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityDTOMapper mapper;

    @Transactional
    public PacienteResponseDTO registrarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        log.info("Registrando nuevo paciente: {}", pacienteRequestDTO);

        Paciente paciente = mapper.toEntity(pacienteRequestDTO);
        if (pacienteRepository.existsByCedula(paciente.getCedula())) {
            log.warn("El paciente ya existe con cedula: {}", pacienteRequestDTO.getCedula());
            throw new DuplicateResourceException("El paciente ya existe con cedula: " + pacienteRequestDTO.getCedula());
        }

        Paciente pacienteCreado = pacienteRepository.save(paciente);
        log.info("Paciente registrado exitosamente: {}", pacienteCreado);

        return mapper.toResponseDTO(pacienteCreado);
    }

    public Optional<PacienteResponseDTO> buscarPacientePorId(Integer id) {
        log.info("Buscando paciente con ID: {}", id);
        Paciente pacienteBuscado = pacienteRepository.findPacienteById(id)
                .orElseThrow(() -> {
                    log.warn("Paciente no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("no se encontro paciente con ID: " + id);
                });

        log.info("Paciente encontrado: {}", pacienteBuscado);
        return Optional.of(ConvertirEntidadADTO(pacienteBuscado));
    }

    Optional<Paciente> buscarPacienteEntidadPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public PacienteResponseDTO actualizarPaciente(Integer id, PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        paciente.setNombre(pacienteRequestDTO.getNombre());
        paciente.setApellido(pacienteRequestDTO.getApellido());
        paciente.setCedula(pacienteRequestDTO.getCedula());
        paciente.setFechaIngreso(pacienteRequestDTO.getFechaIngreso());
        paciente.setEmail(pacienteRequestDTO.getEmail());
        paciente.setDomicilio(mapper.toEntity(pacienteRequestDTO.getDomicilio()));

        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        return mapper.toResponseDTO(pacienteActualizado);
    }

    public void eliminarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public List<PacienteResponseDTO> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteResponseDTO> response = new ArrayList<>();
        for (Paciente p : pacientes) {
            response.add(mapper.toResponseDTO(p));
        }
        return response;
    }

    private Paciente ConvertirResquestDtoAEntidad(PacienteRequestDTO pacienteRequestDTO) {
        return Paciente.builder()
                .nombre(pacienteRequestDTO.getNombre())
                .apellido(pacienteRequestDTO.getApellido())
                .cedula(pacienteRequestDTO.getCedula())
                .fechaIngreso(pacienteRequestDTO.getFechaIngreso())
                .email(pacienteRequestDTO.getEmail())
                .domicilio(ConvertirDtoAEntidad(pacienteRequestDTO.getDomicilio()))
                .build();
    }

    private PacienteResponseDTO ConvertirEntidadADTO(Paciente paciente) {
        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .cedula(paciente.getCedula())
                .fechaIngreso(paciente.getFechaIngreso())
                .email(paciente.getEmail())
                .domicilio(ConvertirEntidadADTO(paciente.getDomicilio()))
                .build();
    }

    private DomicilioResponseDTO ConvertirEntidadADTO(Domicilio domicilio) {
        return DomicilioResponseDTO.builder()
                .id(domicilio.getId())
                .calle(domicilio.getCalle())
                .numero(domicilio.getNumero())
                .localidad(domicilio.getLocalidad())
                .provincia(domicilio.getProvincia())
                .build();
    }

    private Domicilio ConvertirDtoAEntidad(DomicilioRequestDTO domicilioRequestDTO) {
        return Domicilio.builder()
                .calle(domicilioRequestDTO.getCalle())
                .numero(domicilioRequestDTO.getNumero())
                .localidad(domicilioRequestDTO.getLocalidad())
                .provincia(domicilioRequestDTO.getProvincia())
                .build();
    }
}
