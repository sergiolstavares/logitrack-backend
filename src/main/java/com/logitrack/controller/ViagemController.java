package com.logitrack.controller;

import com.logitrack.dto.ApiResponse;
import com.logitrack.dto.ViagemDTO;
import com.logitrack.service.ViagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ViagemDTO>>> listarTodas(
            @RequestParam(required = false) Long veiculoId) {
        List<ViagemDTO> result = veiculoId != null
                ? viagemService.listarPorVeiculo(veiculoId)
                : viagemService.listarTodas();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ViagemDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(viagemService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ViagemDTO>> criar(@Valid @RequestBody ViagemDTO dto) {
        ViagemDTO criada = viagemService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Viagem criada com sucesso", criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ViagemDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody ViagemDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Viagem atualizada com sucesso", viagemService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        viagemService.deletar(id);
        return ResponseEntity.ok(ApiResponse.ok("Viagem removida com sucesso", null));
    }
}
