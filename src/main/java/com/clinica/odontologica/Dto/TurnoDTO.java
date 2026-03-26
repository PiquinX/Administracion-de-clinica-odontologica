package com.clinica.odontologica.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TurnoDTO {
    private Integer id;
    private Integer idPaciente;
    private Integer idOdontologo;
    private LocalDate fechaTurno;
}
