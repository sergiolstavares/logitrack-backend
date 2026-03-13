package com.logitrack.service;

import com.logitrack.dto.DashboardDTO;
import com.logitrack.repository.ManutencaoRepository;
import com.logitrack.repository.VeiculoRepository;
import com.logitrack.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ViagemRepository viagemRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final ManutencaoService manutencaoService;

    public DashboardDTO getDashboard() {
        // Métrica 1: Total KM frota + por veículo
        BigDecimal totalKmFrota = viagemRepository.sumKmTotal();
        List<Object[]> kmRaw = viagemRepository.rankingUtilizacao();
        List<DashboardDTO.KmPorVeiculoDTO> kmPorVeiculo = kmRaw.stream()
                .map(row -> DashboardDTO.KmPorVeiculoDTO.builder()
                        .veiculoId(((Number) row[0]).longValue())
                        .placa((String) row[1])
                        .modelo((String) row[2])
                        .totalKm((BigDecimal) row[3])
                        .build())
                .collect(Collectors.toList());

        // Métrica 2: Volume por categoria
        List<Object[]> volumeRaw = viagemRepository.countViagensByTipoVeiculo();
        List<DashboardDTO.VolumeCategoriaDTO> volumePorCategoria = volumeRaw.stream()
                .map(row -> DashboardDTO.VolumeCategoriaDTO.builder()
                        .tipo((String) row[0])
                        .quantidade(((Number) row[1]).longValue())
                        .build())
                .collect(Collectors.toList());

        // Métrica 3: Próximas 5 manutenções
        var proximasManutencoes = manutencaoRepository.findProximasManutencoes().stream()
                .map(manutencaoService::toDTO)
                .collect(Collectors.toList());

        // Métrica 4: Ranking de utilização
        AtomicInteger posicao = new AtomicInteger(1);
        List<DashboardDTO.RankingDTO> ranking = kmRaw.stream()
                .map(row -> DashboardDTO.RankingDTO.builder()
                        .veiculoId(((Number) row[0]).longValue())
                        .placa((String) row[1])
                        .modelo((String) row[2])
                        .totalKm((BigDecimal) row[3])
                        .posicao(posicao.getAndIncrement())
                        .build())
                .collect(Collectors.toList());

        // Métrica 5: Projeção financeira
        BigDecimal custoMesAtual = manutencaoRepository.sumCustoMesAtual();
        BigDecimal custoTotal = manutencaoRepository.sumCustoTotal();

        // Resumo geral
        long totalVeiculos = veiculoRepository.count();
        long totalViagens = viagemRepository.count();
        long totalManutencoesPendentes = manutencaoRepository.findByStatusOrderByDataInicio("PENDENTE").size();

        return DashboardDTO.builder()
                .totalKmFrota(totalKmFrota)
                .kmPorVeiculo(kmPorVeiculo)
                .volumePorCategoria(volumePorCategoria)
                .proximasManutencoes(proximasManutencoes)
                .rankingUtilizacao(ranking)
                .custoMesAtual(custoMesAtual)
                .custoTotal(custoTotal)
                .totalVeiculos(totalVeiculos)
                .totalViagens(totalViagens)
                .totalManutencoesPendentes(totalManutencoesPendentes)
                .build();
    }
}
