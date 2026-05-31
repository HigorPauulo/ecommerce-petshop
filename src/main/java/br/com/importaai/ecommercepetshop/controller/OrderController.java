package br.com.importaai.ecommercepetshop.controller;

import br.com.importaai.ecommercepetshop.dto.request.OrderRequest;
import br.com.importaai.ecommercepetshop.dto.request.OrderStatusUpdateRequest;
import br.com.importaai.ecommercepetshop.dto.response.OrderResponse;
import br.com.importaai.ecommercepetshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos e seus itens")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cria um pedido com seus itens (baixa o estoque)")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        OrderResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    @Operation(summary = "Lista pedidos (paginado, filtro opcional por cliente)")
    public ResponseEntity<Page<OrderResponse>> findAll(
            @RequestParam(required = false) Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(customerId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por id")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um pedido (cancelar devolve estoque)")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
                                                      @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(service.updateStatus(id, request.status()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
