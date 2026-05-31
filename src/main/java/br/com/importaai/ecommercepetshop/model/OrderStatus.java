package br.com.importaai.ecommercepetshop.model;

/** Estados possiveis de um pedido ao longo do seu ciclo de vida. */
public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
