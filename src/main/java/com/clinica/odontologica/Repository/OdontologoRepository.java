package com.clinica.odontologica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.odontologica.model.Odontologo;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    // here we can add custom queries
    // for example: findByMatricula(String matricula)
}
