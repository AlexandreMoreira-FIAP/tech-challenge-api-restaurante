package br.com.posfiap.restmanager.entities;

import java.time.Instant;

//TODO: Configurar Audit do Spring
public abstract class AbstractAuditingEntity {

    private String createdBy;
    private Instant createdDate = Instant.now();
    private String lastModifiedBy;
    private Instant lastModifiedDate = Instant.now();
}
