package br.com.posfiap.restmanager.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    Boolean somenteConsumoLocal;

    private String foto; // Caminho ou URL da foto

    @ManyToOne
<<<<<<< HEAD:restmanager/src/main/java/br/com/posfiap/restmanager/infrastructure/entities/ItemEntity.java
    @JoinColumn(name = "restaurante_id", nullable = false)
=======
    @JoinColumn(name = "restaurante_id")
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed:restmanager/src/main/java/br/com/posfiap/restmanager/entity/ItemEntity.java
    private RestauranteEntity restaurante;

}
