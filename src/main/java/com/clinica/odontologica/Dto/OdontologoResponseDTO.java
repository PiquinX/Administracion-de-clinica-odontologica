package com.clinica.odontologica.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String matricula;
}
