package br.com.posfiap.restmanager.infrastructure.persistence.entity;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    @Column(unique = true)
    private String login;

    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private LocalDateTime dataUltimaAlteracao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    private List<RestauranteEntity> restaurantes;
}