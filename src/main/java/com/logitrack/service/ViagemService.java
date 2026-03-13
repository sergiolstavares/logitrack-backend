package com.logitrack.service;

import com.logitrack.dto.ViagemDTO;
import com.logitrack.entity.Veiculo;
import com.logitrack.entity.Viagem;
import com.logitrack.repository.VeiculoRepository;
import com.logitrack.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final VeiculoRepository veiculoRepository;

    public List<ViagemDTO> listarTodas() {
        return viagemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ViagemDTO> listarPorVeiculo(Long veiculoId) {
        return viagemRepository.findByVeiculoIdOrderByDataSaidaDesc(veiculoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ViagemDTO buscarPorId(Long id) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada: " + id));
        return toDTO(viagem);
    }

    public ViagemDTO criar(ViagemDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + dto.getVeiculoId()));
        Viagem viagem = toEntity(dto, veiculo);
        return toDTO(viagemRepository.save(viagem));
    }

    public ViagemDTO atualizar(Long id, ViagemDTO dto) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada: " + id));
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + dto.getVeiculoId()));
        viagem.setVeiculo(veiculo);
        viagem.setDataSaida(dto.getDataSaida());
        viagem.setDataChegada(dto.getDataChegada());
        viagem.setOrigem(dto.getOrigem());
        viagem.setDestino(dto.getDestino());
        viagem.setKmPercorrida(dto.getKmPercorrida());
        return toDTO(viagemRepository.save(viagem));
    }

    public void deletar(Long id) {
        if (!viagemRepository.existsById(id)) {
            throw new RuntimeException("Viagem não encontrada: " + id);
        }
        viagemRepository.deleteById(id);
    }

    public ViagemDTO toDTO(Viagem v) {
        return ViagemDTO.builder()
                .id(v.getId())
                .veiculoId(v.getVeiculo().getId())
                .veiculoPlaca(v.getVeiculo().getPlaca())
                .veiculoModelo(v.getVeiculo().getModelo())
                .dataSaida(v.getDataSaida())
                .dataChegada(v.getDataChegada())
                .origem(v.getOrigem())
                .destino(v.getDestino())
                .kmPercorrida(v.getKmPercorrida())
                .build();
    }

    private Viagem toEntity(ViagemDTO dto, Veiculo veiculo) {
        return Viagem.builder()
                .veiculo(veiculo)
                .dataSaida(dto.getDataSaida())
                .dataChegada(dto.getDataChegada())
                .origem(dto.getOrigem())
                .destino(dto.getDestino())
                .kmPercorrida(dto.getKmPercorrida())
                .build();
    }
}
