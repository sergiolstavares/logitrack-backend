package com.logitrack.repository;

import com.logitrack.entity.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    List<Manutencao> findByVeiculoIdOrderByDataInicioDesc(Long veiculoId);

    List<Manutencao> findByStatusOrderByDataInicio(String status);

    // Métrica 3: Próximas 5 manutenções agendadas
    @Query("SELECT m FROM Manutencao m WHERE m.status IN ('PENDENTE', 'EM_REALIZACAO') " +
           "ORDER BY m.dataInicio ASC LIMIT 5")
    List<Manutencao> findProximasManutencoes();

    // Métrica 5: Projeção financeira - custo total do mês atual
    @Query("SELECT COALESCE(SUM(m.custoEstimado), 0) FROM Manutencao m " +
           "WHERE EXTRACT(MONTH FROM m.dataInicio) = EXTRACT(MONTH FROM CURRENT_DATE) " +
           "AND EXTRACT(YEAR FROM m.dataInicio) = EXTRACT(YEAR FROM CURRENT_DATE)")
    BigDecimal sumCustoMesAtual();

    // Total geral de custos
    @Query("SELECT COALESCE(SUM(m.custoEstimado), 0) FROM Manutencao m WHERE m.status != 'CANCELADA'")
    BigDecimal sumCustoTotal();
}
