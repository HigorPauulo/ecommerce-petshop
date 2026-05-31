package br.com.importaai.ecommercepetshop.mapper;

import br.com.importaai.ecommercepetshop.dto.request.ProductRequest;
import br.com.importaai.ecommercepetshop.dto.response.ProductResponse;
import br.com.importaai.ecommercepetshop.model.Category;
import br.com.importaai.ecommercepetshop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request, Category category) {
        Product product = new Product();
        applyValues(product, request, category);
        return product;
    }

    public void updateEntity(Product product, ProductRequest request, Category category) {
        applyValues(product, request, category);
    }

    private void applyValues(Product product, ProductRequest request, Category category) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setImageUrl(request.imageUrl());
        product.setActive(request.active() == null ? Boolean.TRUE : request.active());
        product.setCategory(category);
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getActive(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }
}
