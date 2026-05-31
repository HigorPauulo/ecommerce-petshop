package br.com.importaai.ecommercepetshop.dto.response;

import br.com.importaai.ecommercepetshop.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long customerId,
        String customerName,
        LocalDateTime orderDate,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemResponse> items
) {
}
