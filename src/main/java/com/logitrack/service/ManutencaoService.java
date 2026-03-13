package com.logitrack.service;

import com.logitrack.dto.ManutencaoDTO;
import com.logitrack.entity.Manutencao;
import com.logitrack.entity.Veiculo;
import com.logitrack.repository.ManutencaoRepository;
import com.logitrack.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final VeiculoRepository veiculoRepository;

    public List<ManutencaoDTO> listarTodas() {
        return manutencaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ManutencaoDTO> listarPorVeiculo(Long veiculoId) {
        return manutencaoRepository.findByVeiculoIdOrderByDataInicioDesc(veiculoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ManutencaoDTO buscarPorId(Long id) {
        Manutencao m = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada: " + id));
        return toDTO(m);
    }

    public ManutencaoDTO criar(ManutencaoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + dto.getVeiculoId()));
        Manutencao m = toEntity(dto, veiculo);
        return toDTO(manutencaoRepository.save(m));
    }

    public ManutencaoDTO atualizar(Long id, ManutencaoDTO dto) {
        Manutencao m = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada: " + id));
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + dto.getVeiculoId()));
        m.setVeiculo(veiculo);
        m.setDataInicio(dto.getDataInicio());
        m.setDataFinalizacao(dto.getDataFinalizacao());
        m.setTipoServico(dto.getTipoServico());
        m.setCustoEstimado(dto.getCustoEstimado());
        m.setStatus(dto.getStatus());
        return toDTO(manutencaoRepository.save(m));
    }

    public void deletar(Long id) {
        if (!manutencaoRepository.existsById(id)) {
            throw new RuntimeException("Manutenção não encontrada: " + id);
        }
        manutencaoRepository.deleteById(id);
    }

    public ManutencaoDTO toDTO(Manutencao m) {
        return ManutencaoDTO.builder()
                .id(m.getId())
                .veiculoId(m.getVeiculo().getId())
                .veiculoPlaca(m.getVeiculo().getPlaca())
                .veiculoModelo(m.getVeiculo().getModelo())
                .dataInicio(m.getDataInicio())
                .dataFinalizacao(m.getDataFinalizacao())
                .tipoServico(m.getTipoServico())
                .custoEstimado(m.getCustoEstimado())
                .status(m.getStatus())
                .build();
    }

    private Manutencao toEntity(ManutencaoDTO dto, Veiculo veiculo) {
        return Manutencao.builder()
                .veiculo(veiculo)
                .dataInicio(dto.getDataInicio())
                .dataFinalizacao(dto.getDataFinalizacao())
                .tipoServico(dto.getTipoServico())
                .custoEstimado(dto.getCustoEstimado())
                .status(dto.getStatus() != null ? dto.getStatus() : "PENDENTE")
                .build();
    }
}
