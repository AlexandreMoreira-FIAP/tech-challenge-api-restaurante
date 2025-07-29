package br.com.posfiap.restmanager.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private String tipoUsuario;

    private LocalDateTime dataUltimaAlteracao;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER) // referência ao nome da lista em RestauranteEntity
    private List<RestauranteEntity> restaurantes;
}