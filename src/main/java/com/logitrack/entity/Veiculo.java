package com.logitrack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String placa;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(length = 20)
    private String tipo; // LEVE ou PESADO

    private Integer ano;
}
