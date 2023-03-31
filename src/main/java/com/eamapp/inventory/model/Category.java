package com.eamapp.inventory.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "category",
        indexes = {@Index(name = "index_name", columnList = "name", unique = true)})
public class Category implements Serializable{
    @Serial
    private static final long serialVersionUID = 7138772029798401102L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
