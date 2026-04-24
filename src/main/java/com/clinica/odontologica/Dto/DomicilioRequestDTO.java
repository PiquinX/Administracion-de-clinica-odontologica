package com.clinica.odontologica.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioRequestDTO {
    @NotBlank(message = "La calle es obligatoria")
    private String calle;
    @NotNull(message = "El número es obligatorio")
    private Integer numero;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;
}
