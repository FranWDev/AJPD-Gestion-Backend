package org.dubini.gestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("centros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Centro {
    @Id
    private Long id;
    private String nombre;
}
