package br.com.importaai.ecommercepetshop.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(

        @NotNull(message = "O produto e obrigatorio")
        Long productId,

        @NotNull(message = "A quantidade e obrigatoria")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity
) {
}
