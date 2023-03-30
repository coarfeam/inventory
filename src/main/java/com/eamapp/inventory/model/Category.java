package com.eamapp.inventory.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(
        name = "category",
        indexes = {@Index(name = "index_name", columnList = "name", unique = true)})
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
