package org.dubini.gestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("historial_cargos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCargo {
    @Id
    private Long id;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long cargoId;
}
