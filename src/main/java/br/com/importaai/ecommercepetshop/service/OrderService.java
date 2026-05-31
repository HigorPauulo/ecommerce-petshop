package br.com.importaai.ecommercepetshop.service;

import br.com.importaai.ecommercepetshop.dto.request.OrderItemRequest;
import br.com.importaai.ecommercepetshop.dto.request.OrderRequest;
import br.com.importaai.ecommercepetshop.dto.response.OrderResponse;
import br.com.importaai.ecommercepetshop.exception.BusinessException;
import br.com.importaai.ecommercepetshop.exception.ResourceNotFoundException;
import br.com.importaai.ecommercepetshop.mapper.OrderMapper;
import br.com.importaai.ecommercepetshop.model.Customer;
import br.com.importaai.ecommercepetshop.model.Order;
import br.com.importaai.ecommercepetshop.model.OrderItem;
import br.com.importaai.ecommercepetshop.model.OrderStatus;
import br.com.importaai.ecommercepetshop.model.Product;
import br.com.importaai.ecommercepetshop.repository.CustomerRepository;
import br.com.importaai.ecommercepetshop.repository.OrderRepository;
import br.com.importaai.ecommercepetshop.repository.ProductRepository;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> ResourceNotFoundException.of("Cliente", request.customerId()));

        Order order = new Order();
        order.setCustomer(customer);

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Produto", itemRequest.productId()));

            if (Boolean.FALSE.equals(product.getActive())) {
                throw new BusinessException("O produto '" + product.getName() + "' nao esta disponivel");
            }
            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new BusinessException("Estoque insuficiente para o produto '" + product.getName()
                        + "'. Disponivel: " + product.getStockQuantity());
            }

            // Baixa de estoque e congelamento do preco no momento da compra
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(product.getPrice());
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));
            order.addItem(item);
        }

        order.recalculateTotal();
        return mapper.toResponse(orderRepository.save(order));
    }

    public Page<OrderResponse> findAll(Long customerId, Pageable pageable) {
        Page<Order> orders = (customerId == null)
                ? orderRepository.findAll(pageable)
                : orderRepository.findByCustomerId(customerId, pageable);
        return orders.map(mapper::toResponse);
    }

    public OrderResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus newStatus) {
        Order order = getEntity(id);

        // Cancelar um pedido ainda nao cancelado devolve os itens ao estoque
        if (newStatus == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.CANCELLED) {
            order.getItems().forEach(item -> {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            });
        }

        order.setStatus(newStatus);
        return mapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = getEntity(id);
        orderRepository.delete(order);
    }

    private Order getEntity(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Pedido", id));
    }
}
