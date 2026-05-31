package br.com.importaai.ecommercepetshop.mapper;

import br.com.importaai.ecommercepetshop.dto.response.OrderItemResponse;
import br.com.importaai.ecommercepetshop.dto.response.OrderResponse;
import br.com.importaai.ecommercepetshop.model.Order;
import br.com.importaai.ecommercepetshop.model.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(this::toItemResponse)
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotal(),
                items);
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal());
    }
}
