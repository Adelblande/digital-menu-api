package com.api.digitalmenu.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Entity
@Table(name = "tb_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private LocalDateTime created_date;
}
