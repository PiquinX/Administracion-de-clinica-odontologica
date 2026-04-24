package com.clinica.odontologica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.clinica.odontologica.Dto.DomicilioRequestDTO;
import com.clinica.odontologica.Dto.PacienteRequestDTO;
import com.clinica.odontologica.Dto.PacienteResponseDTO;
import com.clinica.odontologica.Repository.PacienteRepository;
import com.clinica.odontologica.exceptions.DuplicateResourceException;
import com.clinica.odontologica.exceptions.ResourceNotFoundException;
import com.clinica.odontologica.mapper.EntityDTOMapper;
import com.clinica.odontologica.model.Domicilio;
import com.clinica.odontologica.model.Paciente;
import com.clinica.odontologica.service.PacienteService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteTestService {
    @InjectMocks
    private PacienteService pacienteService;
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private EntityDTOMapper mapper;

    private PacienteRequestDTO pacienteRequestDTO;
    private PacienteResponseDTO pacienteResponseDTO;
    private Paciente paciente;

    /**
     * Set up the test data before each test.
     * Initializes a standard Paciente, RequestDTO, and ResponseDTO.
     */
    @BeforeEach
    void setUp() {
        Domicilio domicilio = Domicilio.builder()
                .calle("Av. Siempre Viva")
                .numero(742)
                .localidad("Springfield")
                .provincia("Buenos Aires")
                .build();

        DomicilioRequestDTO domicilioRequestDTO = DomicilioRequestDTO.builder()
                .calle("Av. Siempre Viva")
                .numero(742)
                .localidad("Springfield")
                .provincia("Buenos Aires")
                .build();

        paciente = Paciente.builder()
                .nombre("Bart")
                .apellido("Simpson")
                .cedula("12345678")
                .email("bartSimpson@gmail.com")
                .fechaIngreso(LocalDate.now())
                .domicilio(domicilio)
                .build();

        pacienteRequestDTO = PacienteRequestDTO.builder()
                .nombre("Bart")
                .apellido("Simpson")
                .cedula("12345678")
                .email("bartSimpson@gmail.com")
                .fechaIngreso(LocalDate.now())
                .domicilio(domicilioRequestDTO)
                .build();

        pacienteResponseDTO = PacienteResponseDTO.builder()
                .id(1)
                .nombre("Bart")
                .apellido("Simpson")
                .cedula("12345678")
                .email("bartSimpson@gmail.com")
                .fechaIngreso(LocalDate.now())
                .build();
    }

    /**
     * Test successful patient registration.
     * Verifies that the service correctly maps DTOs and saves the entity.
     */
    @Test
    @DisplayName("Crear paciente valido")
    void crearPacienteValido() {
        when(pacienteRepository.existsByCedula(anyString())).thenReturn(false);
        when(mapper.toEntity(any(PacienteRequestDTO.class))).thenReturn(paciente);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        when(mapper.toResponseDTO(any(Paciente.class))).thenReturn(pacienteResponseDTO);

        PacienteResponseDTO resultado = pacienteService.registrarPaciente(pacienteRequestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(pacienteResponseDTO);

        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(pacienteRepository, times(1)).existsByCedula("12345678");
    }

    /**
     * Test patient registration with invalid (empty) data.
     * Note: This test simulates the behavior of validation constraints.
     */
    @Test
    @DisplayName("Crear paciente invalido con datos vacios")
    void crearPacienteInvalidoConDatosVacios() {
        pacienteRequestDTO.setNombre(null);
        pacienteRequestDTO.setApellido(null);
        pacienteRequestDTO.setCedula(null);
        pacienteRequestDTO.setEmail(null);
        pacienteRequestDTO.setFechaIngreso(null);
        pacienteRequestDTO.setDomicilio(null);

        assertThatThrownBy(() -> pacienteService.registrarPaciente(pacienteRequestDTO))
                .isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessageContaining("No puede estar vacio");

        verify(pacienteRepository, times(0)).save(any(Paciente.class));
    }

    /**
     * Test registration of a patient that already exists in the database.
     * Verifies that DuplicateResourceException is thrown.
     */
    @Test
    @DisplayName("Crear paciente existente")
    void crearPacienteExistente() {
        when(pacienteRepository.existsByCedula(anyString())).thenReturn(true);

        assertThatThrownBy(() -> pacienteService.registrarPaciente(pacienteRequestDTO))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("El paciente ya existe con cedula: 12345678");

        verify(pacienteRepository, times(0)).save(any(Paciente.class));
    }

    /**
     * Test searching for a patient by an ID that exists.
     */
    @Test
    @DisplayName("Buscar paciente por ID existente")
    void buscarPacientePorIdExistente() {
        when(pacienteRepository.findPacienteById(1)).thenReturn(Optional.of(paciente));
        when(mapper.toResponseDTO(any(Paciente.class))).thenReturn(pacienteResponseDTO);

        Optional<PacienteResponseDTO> resultado = pacienteService.buscarPacientePorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(pacienteResponseDTO);
        verify(pacienteRepository, times(1)).findPacienteById(1);
    }

    /**
     * Test searching for a patient by an ID that does not exist.
     */
    @Test
    @DisplayName("Buscar paciente por ID no existente")
    void buscarPacientePorIdNoExistente() {
        when(pacienteRepository.findPacienteById(1)).thenReturn(Optional.empty());

        Optional<PacienteResponseDTO> resultado = pacienteService.buscarPacientePorId(1);

        assertThat(resultado).isNotPresent();
        verify(pacienteRepository, times(1)).findPacienteById(1);
    }

    /**
     * Test updating an existing patient's information.
     */
    @Test
    @DisplayName("Actualizar paciente existente")
    void actualizarPacienteExistente() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        when(mapper.toResponseDTO(any(Paciente.class))).thenReturn(pacienteResponseDTO);

        PacienteResponseDTO resultado = pacienteService.actualizarPaciente(1, pacienteRequestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(pacienteResponseDTO);
        verify(pacienteRepository, times(1)).findById(1);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    /**
     * Test updating a patient that does not exist.
     * Verifies that ResourceNotFoundException is thrown.
     */
    @Test
    @DisplayName("Actualizar paciente no existente")
    void actualizarPacienteNoExistente() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pacienteService.actualizarPaciente(1, pacienteRequestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Paciente no encontrado con ID: 1");

        verify(pacienteRepository, times(1)).findById(1);
        verify(pacienteRepository, times(0)).save(any(Paciente.class));
    }

    /**
     * Test deleting an existing patient.
     */
    @Test
    @DisplayName("Eliminar paciente existente")
    void eliminarPacienteExistente() {
        when(pacienteRepository.existsById(1)).thenReturn(true);

        pacienteService.eliminarPaciente(1);

        verify(pacienteRepository, times(1)).existsById(1);
        verify(pacienteRepository, times(1)).deleteById(1);
    }

    /**
     * Test deleting a patient that does not exist.
     */
    @Test
    @DisplayName("Eliminar paciente no existente")
    void eliminarPacienteNoExistente() {
        when(pacienteRepository.existsById(1)).thenReturn(false);

        assertThatThrownBy(() -> pacienteService.eliminarPaciente(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se puede eliminar, paciente no encontrado con ID: 1");

        verify(pacienteRepository, times(1)).existsById(1);
        verify(pacienteRepository, times(0)).deleteById(1);
    }

    /**
     * Test listing all patients.
     */
    @Test
    @DisplayName("Listar pacientes")
    void listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(paciente);

        when(pacienteRepository.findAll()).thenReturn(pacientes);
        when(mapper.toResponseDTO(any(Paciente.class))).thenReturn(pacienteResponseDTO);

        List<PacienteResponseDTO> resultado = pacienteService.listarPacientes();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(pacienteResponseDTO);
        verify(pacienteRepository, times(1)).findAll();
    }
}
