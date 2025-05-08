package br.com.posfiap.restmanager.entities;

import br.com.posfiap.restmanager.entities.enumeration.AddressTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "address")
public class Address extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    //@NotNull
    @Column(name= "address_uuid", nullable = false, unique = true)
    private UUID addressUuid;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressTypes addressType;

    //@NotNull
    @Column(name = "city", nullable = false)
    private String city;

    //@NotNull
    @Column(name = "state", nullable = false)
    private String state;

    //@NotNull
    @Column(name = "country", nullable = false)
    private String country;

    //@NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    //@NotNull
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customer customer;
}
