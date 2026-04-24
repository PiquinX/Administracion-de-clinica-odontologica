package com.clinica.odontologica.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String cedula;
    private LocalDate fechaIngreso;
    private String email;
    private DomicilioResponseDTO domicilio;
}
