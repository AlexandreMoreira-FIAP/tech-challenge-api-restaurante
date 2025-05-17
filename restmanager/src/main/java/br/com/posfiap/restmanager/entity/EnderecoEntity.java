package br.com.posfiap.restmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String rua;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String cep;

    private String estado;

    private String pais;
}