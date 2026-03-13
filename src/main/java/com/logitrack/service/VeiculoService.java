package com.logitrack.service;

import com.logitrack.dto.VeiculoDTO;
import com.logitrack.entity.Veiculo;
import com.logitrack.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public List<VeiculoDTO> listarTodos() {
        return veiculoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VeiculoDTO buscarPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + id));
        return toDTO(veiculo);
    }

    public VeiculoDTO criar(VeiculoDTO dto) {
        if (veiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new RuntimeException("Já existe um veículo com a placa: " + dto.getPlaca());
        }
        Veiculo veiculo = toEntity(dto);
        return toDTO(veiculoRepository.save(veiculo));
    }

    public VeiculoDTO atualizar(Long id, VeiculoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + id));
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setTipo(dto.getTipo());
        veiculo.setAno(dto.getAno());
        return toDTO(veiculoRepository.save(veiculo));
    }

    public void deletar(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new RuntimeException("Veículo não encontrado: " + id);
        }
        veiculoRepository.deleteById(id);
    }

    public VeiculoDTO toDTO(Veiculo v) {
        return VeiculoDTO.builder()
                .id(v.getId())
                .placa(v.getPlaca())
                .modelo(v.getModelo())
                .tipo(v.getTipo())
                .ano(v.getAno())
                .build();
    }

    private Veiculo toEntity(VeiculoDTO dto) {
        return Veiculo.builder()
                .placa(dto.getPlaca())
                .modelo(dto.getModelo())
                .tipo(dto.getTipo())
                .ano(dto.getAno())
                .build();
    }
}
