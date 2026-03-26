package com.clinica.odontologica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.odontologica.model.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

}
