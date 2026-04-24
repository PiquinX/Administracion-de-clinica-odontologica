package com.clinica.odontologica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.odontologica.Dto.OdontologoRequestDTO;
import com.clinica.odontologica.Dto.OdontologoResponseDTO;
import com.clinica.odontologica.Repository.OdontologoRepository;
import com.clinica.odontologica.exceptions.ResourceNotFoundException;
import com.clinica.odontologica.model.Odontologo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public OdontologoResponseDTO registrarOdontologo(OdontologoRequestDTO dto) {
        Odontologo odontologo = Odontologo.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .matricula(dto.getMatricula())
                .build();
        return odontologoAResponseDTO(odontologoRepository.save(odontologo));
    }

    public Optional<OdontologoResponseDTO> buscarOdontologoPorId(Integer id) {
        log.info("Buscando odontologo con ID: {}", id);
        Odontologo o = odontologoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Odontologo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Odontologo no encontrado con ID: " + id);
                });
        
        return Optional.of(odontologoAResponseDTO(o));
    }

    Optional<Odontologo> buscarOdontologoEntidadPorId(Integer id) {
        return odontologoRepository.findById(id);
    }

    public OdontologoResponseDTO actualizarOdontologo(Integer id, OdontologoRequestDTO dto) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontologo no encontrado con ID: " + id));
        odontologo.setNombre(dto.getNombre());
        odontologo.setApellido(dto.getApellido());
        odontologo.setMatricula(dto.getMatricula());
        return odontologoAResponseDTO(odontologoRepository.save(odontologo));
    }

    public void eliminarOdontologo(Integer id) {
        if (!odontologoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, odontologo no encontrado con ID: " + id);
        }
        odontologoRepository.deleteById(id);
    }

    public List<OdontologoResponseDTO> listarOdontologos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoResponseDTO> response = new ArrayList<>();
        for (Odontologo o : odontologos) {
            response.add(odontologoAResponseDTO(o));
        }
        return response;
    }

    private OdontologoResponseDTO odontologoAResponseDTO(Odontologo o) {
        return OdontologoResponseDTO.builder()
                .id(o.getId())
                .nombre(o.getNombre())
                .apellido(o.getApellido())
                .matricula(o.getMatricula())
                .build();
    }
}
