package com.spring.boot.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imagePath;
    @Column(length = 1000)
    private String description;
    private Double price;

@ManyToOne
    private Category category;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
