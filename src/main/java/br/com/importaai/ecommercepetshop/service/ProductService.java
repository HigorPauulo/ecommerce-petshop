package br.com.importaai.ecommercepetshop.service;

import br.com.importaai.ecommercepetshop.dto.request.ProductRequest;
import br.com.importaai.ecommercepetshop.dto.response.ProductResponse;
import br.com.importaai.ecommercepetshop.exception.ResourceNotFoundException;
import br.com.importaai.ecommercepetshop.mapper.ProductMapper;
import br.com.importaai.ecommercepetshop.model.Category;
import br.com.importaai.ecommercepetshop.model.Product;
import br.com.importaai.ecommercepetshop.repository.CategoryRepository;
import br.com.importaai.ecommercepetshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = getCategory(request.categoryId());
        Product saved = productRepository.save(mapper.toEntity(request, category));
        return mapper.toResponse(saved);
    }

    public Page<ProductResponse> findAll(Long categoryId, Pageable pageable) {
        Page<Product> products = (categoryId == null)
                ? productRepository.findAll(pageable)
                : productRepository.findByCategoryId(categoryId, pageable);
        return products.map(mapper::toResponse);
    }

    public ProductResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getEntity(id);
        Category category = getCategory(request.categoryId());
        mapper.updateEntity(product, request, category);
        return mapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = getEntity(id);
        productRepository.delete(product);
    }

    private Product getEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Produto", id));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Categoria", id));
    }
}
