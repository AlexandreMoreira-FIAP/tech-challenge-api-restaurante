package br.com.posfiap.restmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "donos_restaurante")
public class DonoRestaurante extends User {

    private String cnpj;

    // Getters e Setters
}
