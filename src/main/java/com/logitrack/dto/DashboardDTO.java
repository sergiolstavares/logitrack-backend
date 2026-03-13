package com.logitrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    // Métrica 1
    private BigDecimal totalKmFrota;
    private List<KmPorVeiculoDTO> kmPorVeiculo;

    // Métrica 2
    private List<VolumeCategoriaDTO> volumePorCategoria;

    // Métrica 3
    private List<ManutencaoDTO> proximasManutencoes;

    // Métrica 4
    private List<RankingDTO> rankingUtilizacao;

    // Métrica 5
    private BigDecimal custoMesAtual;
    private BigDecimal custoTotal;

    // Resumo geral
    private Long totalVeiculos;
    private Long totalViagens;
    private Long totalManutencoesPendentes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class KmPorVeiculoDTO {
        private Long veiculoId;
        private String placa;
        private String modelo;
        private BigDecimal totalKm;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VolumeCategoriaDTO {
        private String tipo;
        private Long quantidade;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RankingDTO {
        private Long veiculoId;
        private String placa;
        private String modelo;
        private BigDecimal totalKm;
        private Integer posicao;
    }
}
