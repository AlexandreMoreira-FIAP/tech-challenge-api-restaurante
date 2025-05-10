package br.com.posfiap.restmanager.entities;

import br.com.posfiap.restmanager.entities.enumeration.UserTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    //@NotNull
    @Column(name= "user_uuid", nullable = false, unique = true)
    private UUID userUuid;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserTypes userType;

    //@NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    //@NotNull
    @Column(name = "email", nullable = false)
    private String email;

    //@NotNull
    @Column(name = "password", nullable = false)
    private String password;

}
