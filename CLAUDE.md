# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and run
./mvnw spring-boot:run

# Build only
./mvnw package

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName
```

## Architecture

This is a Spring Boot 4.x REST API for a dental clinic, using H2 as a file-based database (`~/clinicaOdontologicaTest`), JPA/Hibernate for ORM, and Lombok for boilerplate reduction.

### Two persistence layers (important)

There are **two separate persistence mechanisms** coexisting:

1. **Legacy DAO layer** (`dao/` package): Manual JDBC via `DB.java`. `DB.crearTablas()` is called at startup in `OdontologicaApplication` — it drops and recreates the `DOMICILIOS` and `PACIENTES` tables and inserts seed data every time the app starts. Only `Paciente` and `Domicilio` have DAO implementations; `Odontologo` does not.

2. **Spring Data JPA layer** (`Repository/` package): JPA repositories (`PacienteRepository`, `OdontologoRepository`, `TurnoRepository`) extending `JpaRepository`. All controllers use services that delegate to these repositories.

The DAO layer is legacy and the application's active CRUD goes through JPA repositories. The `iDao<T>` interface in the `dao/` package defines the DAO contract.

### Domain model relationships

- `Paciente` has a `@OneToOne` to `Domicilio` (cascaded), and `@OneToMany` to `Turno` (lazy, JSON-ignored)
- `Odontologo` has `@OneToMany` to `Turno` (lazy, JSON-ignored)
- `Turno` has `@ManyToOne` to both `Paciente` and `Odontologo`
- `Estado` enum: `PROGRAMADO` (default), `CANCELADO`, `COMPLETADO`

### DTO pattern for Turno

`TurnoController` POST/PUT responses return `TurnoDTO` (not `Turno`) to avoid circular serialization. `TurnoDTO` uses flat integer IDs (`idPaciente`, `idOdontologo`) instead of nested objects. GET endpoints return the full `Turno` entity directly.

`TurnoService.guardarTurno()` validates that both `paciente` and `odontologo` IDs exist before saving and defaults `estado` to `PROGRAMADO` if null.

### REST endpoints

| Resource     | Base path        |
|--------------|------------------|
| Paciente     | `/api-paciente`  |
| Odontologo   | `/api-odontologo`|
| Turno        | `/api-turno`     |

All follow standard CRUD: `GET /`, `GET /{id}`, `POST /`, `PUT /`, `DELETE /{id}`.

### H2 Console

Available at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:~/clinicaOdontologicaTest`, user `sa`, password `sa`.

### Logging

App logs to `resultado.log` (file) and console. Package `com.clinica.odontologica` logs at DEBUG; root at INFO.
