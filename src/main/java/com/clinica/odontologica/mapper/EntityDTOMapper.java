package com.clinica.odontologica.mapper;

import com.clinica.odontologica.Dto.DomicilioRequestDTO;
import com.clinica.odontologica.Dto.DomicilioResponseDTO;
import com.clinica.odontologica.Dto.PacienteRequestDTO;
import com.clinica.odontologica.Dto.PacienteResponseDTO;
import com.clinica.odontologica.model.Domicilio;
import com.clinica.odontologica.model.Paciente;

public interface EntityDTOMapper {
    Paciente toEntity(PacienteRequestDTO dto);
    PacienteResponseDTO toResponseDTO(Paciente paciente);

    Domicilio toEntity(DomicilioRequestDTO dto);
    DomicilioResponseDTO toResponseDTO(Domicilio domicilio);
}
