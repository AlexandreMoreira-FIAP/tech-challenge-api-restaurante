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

    String horarioFuncionamento;

    String login;

    String senha;

    TipoUsuario tipoUsuario;

    private LocalDateTime dataUltimaAlteracao;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_proprietario_id")
    private UsuarioEntity usuarioProprietario;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "restaurante_usuario",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioEntity> usuarios;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<ItemEntity> itens;

}