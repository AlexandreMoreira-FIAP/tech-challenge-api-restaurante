package br.com.posfiap.restmanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer  extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    //@NotNull
    @Column(name= "customer_uuid", nullable = false, unique = true)
    private UUID customerUuid;

    //@NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    //@NotNull
    @Column(name = "email", nullable = false)
    private String email;

    //@NotNull
    @Column(name = "password", nullable = false)
    private String password;

}
