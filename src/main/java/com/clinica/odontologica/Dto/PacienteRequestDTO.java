package com.clinica.odontologica.Dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;
    @NotBlank(message = "La cedula es obligatoria")
    @Positive(message = "La cedula debe ser un numero positivo")
    private String cedula;
    // If there is no Date provided the actual date will be used
    private LocalDate fechaIngreso;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser un email valido")
    private String email;
    @Valid
    private DomicilioRequestDTO domicilio;
}
