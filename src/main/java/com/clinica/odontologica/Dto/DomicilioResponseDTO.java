package com.clinica.odontologica.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioResponseDTO {
    private Integer id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;
}
