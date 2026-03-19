package com.clinica.odontologica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.odontologica.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

}
