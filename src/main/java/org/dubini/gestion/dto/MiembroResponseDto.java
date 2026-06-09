package org.dubini.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiembroResponseDto {
    private Long id;
    private String nombreRazonSocial;
    private CentroDto centro;
    private String telefono;
    private String correo;
    private CargoDto cargo;
    private LocalDate fechaCargo;
    private String enlaceWhatsapp;
    private String nifCif;
    private String nacionalidad;
    private String domicilio;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private String observaciones;
    private LocalDate fechaBaja;
    private Set<HistorialCargoDto> historialCargos;
}
