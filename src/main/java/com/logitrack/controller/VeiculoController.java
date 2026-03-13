package com.logitrack.controller;

import com.logitrack.dto.ApiResponse;
import com.logitrack.dto.VeiculoDTO;
import com.logitrack.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VeiculoDTO>>> listarTodos() {
        return ResponseEntity.ok(ApiResponse.ok(veiculoService.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VeiculoDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(veiculoService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VeiculoDTO>> criar(@Valid @RequestBody VeiculoDTO dto) {
        VeiculoDTO criado = veiculoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Veículo criado com sucesso", criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VeiculoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Veículo atualizado com sucesso", veiculoService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.ok(ApiResponse.ok("Veículo removido com sucesso", null));
    }
}
