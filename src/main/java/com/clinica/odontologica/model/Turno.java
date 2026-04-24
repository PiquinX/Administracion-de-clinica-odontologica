package com.clinica.odontologica.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "turnos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "id", "paciente", "odontologo", "fechaTurno", "observaciones", "estado" })
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "odontologo_id", referencedColumnName = "id")
    private Odontologo odontologo;
    private LocalDate fechaTurno;
    private String observaciones;
    private Estado estado;
}
