package com.logitrack.controller;

import com.logitrack.dto.ApiResponse;
import com.logitrack.dto.ManutencaoDTO;
import com.logitrack.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ManutencaoDTO>>> listarTodas(
            @RequestParam(required = false) Long veiculoId) {
        List<ManutencaoDTO> result = veiculoId != null
                ? manutencaoService.listarPorVeiculo(veiculoId)
                : manutencaoService.listarTodas();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ManutencaoDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(manutencaoService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ManutencaoDTO>> criar(@Valid @RequestBody ManutencaoDTO dto) {
        ManutencaoDTO criada = manutencaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Manutenção agendada com sucesso", criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ManutencaoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody ManutencaoDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Manutenção atualizada com sucesso", manutencaoService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        manutencaoService.deletar(id);
        return ResponseEntity.ok(ApiResponse.ok("Manutenção removida com sucesso", null));
    }
}
