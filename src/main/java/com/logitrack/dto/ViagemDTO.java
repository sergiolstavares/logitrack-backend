package com.logitrack.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViagemDTO {
    private Long id;

    @NotNull(message = "Veículo é obrigatório")
    private Long veiculoId;

    private String veiculoPlaca;
    private String veiculoModelo;

    @NotNull(message = "Data de saída é obrigatória")
    private LocalDateTime dataSaida;

    private LocalDateTime dataChegada;

    private String origem;
    private String destino;
    private BigDecimal kmPercorrida;
}
