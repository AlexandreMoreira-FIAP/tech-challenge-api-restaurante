package br.com.posfiap.restmanager.infrastructure.entities;


import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
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
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String nome;

    String tipoDeCozinha;

    String login;

    String senha;

    TipoUsuario tipoUsuario;

    private LocalDateTime dataUltimaAlteracao;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "restaurante_usuario", // nome da tabela de junção
            joinColumns = @JoinColumn(name = "restaurante_id"), // FK desta entidade
            inverseJoinColumns = @JoinColumn(name = "usuario_id") // FK da outra entidade
    )
    private List<UsuarioEntity> usuarios;

}