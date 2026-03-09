package br.edu.ufop.web.sales.controller;

import br.edu.ufop.web.sales.business.services.SaleService;
import br.edu.ufop.web.sales.controller.dtos.sales.CreateSaleDTO;
import br.edu.ufop.web.sales.controller.dtos.sales.SaleDTO;
import br.edu.ufop.web.sales.controller.dtos.sales.UpdateSaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAll() {
        return ResponseEntity.ok(saleService.getAll());
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@RequestBody CreateSaleDTO createSaleDTO) {
        return ResponseEntity.ok(saleService.create(createSaleDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(saleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable UUID id, @RequestBody UpdateSaleDTO updateSaleDTO) {
        return ResponseEntity.ok(saleService.update(id, updateSaleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
