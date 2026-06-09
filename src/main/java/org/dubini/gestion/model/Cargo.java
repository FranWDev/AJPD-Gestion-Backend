package org.dubini.gestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("cargos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {
    @Id
    private Long id;
    private String nombre;
}
