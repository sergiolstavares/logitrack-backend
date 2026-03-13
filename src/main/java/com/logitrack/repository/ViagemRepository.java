package com.logitrack.repository;

import com.logitrack.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByVeiculoIdOrderByDataSaidaDesc(Long veiculoId);

    // Métrica 1: Total de KM por veículo específico
    @Query("SELECT COALESCE(SUM(v.kmPercorrida), 0) FROM Viagem v WHERE v.veiculo.id = :veiculoId")
    BigDecimal sumKmByVeiculoId(@Param("veiculoId") Long veiculoId);

    // Métrica 1: Total de KM de toda a frota
    @Query("SELECT COALESCE(SUM(v.kmPercorrida), 0) FROM Viagem v")
    BigDecimal sumKmTotal();

    // Métrica 2: Volume por categoria (Leve vs Pesado)
    @Query("SELECT v.veiculo.tipo, COUNT(v) FROM Viagem v GROUP BY v.veiculo.tipo")
    List<Object[]> countViagensByTipoVeiculo();

    // Métrica 4: Ranking de utilização (veículo com maior km)
    @Query("SELECT v.veiculo.id, v.veiculo.placa, v.veiculo.modelo, SUM(v.kmPercorrida) as totalKm " +
           "FROM Viagem v GROUP BY v.veiculo.id, v.veiculo.placa, v.veiculo.modelo " +
           "ORDER BY totalKm DESC")
    List<Object[]> rankingUtilizacao();
}
