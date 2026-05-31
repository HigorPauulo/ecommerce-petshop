package br.com.importaai.ecommercepetshop.dto.request;

import br.com.importaai.ecommercepetshop.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(

        @NotNull(message = "O status e obrigatorio")
        OrderStatus status
) {
}
