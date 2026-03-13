package com.logitrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManutencaoDTO {
    private Long id;

    @NotNull(message = "Veículo é obrigatório")
    private Long veiculoId;

    private String veiculoPlaca;
    private String veiculoModelo;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFinalizacao;

    @NotBlank(message = "Tipo de serviço é obrigatório")
    private String tipoServico;

    private BigDecimal custoEstimado;

    private String status;
}
