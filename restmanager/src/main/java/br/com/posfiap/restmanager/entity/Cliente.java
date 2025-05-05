package br.com.posfiap.restmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "clientes")
public class Cliente extends User {
    // Pode incluir dados específicos do cliente no futuro
}