package com.clinica.odontologica.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.odontologica.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // here we can add custom queries
    // for example: findByCedula(String cedula)

    Optional<Paciente> findPacienteById(Integer id);

    boolean existsByCedula(String cedula);
}
