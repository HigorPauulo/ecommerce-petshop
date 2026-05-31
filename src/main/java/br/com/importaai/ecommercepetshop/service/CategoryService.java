package br.com.importaai.ecommercepetshop.service;

import br.com.importaai.ecommercepetshop.dto.request.CategoryRequest;
import br.com.importaai.ecommercepetshop.dto.response.CategoryResponse;
import br.com.importaai.ecommercepetshop.exception.BusinessException;
import br.com.importaai.ecommercepetshop.exception.ResourceNotFoundException;
import br.com.importaai.ecommercepetshop.mapper.CategoryMapper;
import br.com.importaai.ecommercepetshop.model.Category;
import br.com.importaai.ecommercepetshop.repository.CategoryRepository;
import br.com.importaai.ecommercepetshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new BusinessException("Ja existe uma categoria com o nome '" + request.name() + "'");
        }
        Category saved = categoryRepository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(mapper::toResponse);
    }

    public CategoryResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getEntity(id);
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new BusinessException("Ja existe uma categoria com o nome '" + request.name() + "'");
        }
        mapper.updateEntity(category, request);
        return mapper.toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = getEntity(id);
        if (productRepository.existsByCategoryId(id)) {
            throw new BusinessException("Nao e possivel excluir uma categoria que possui produtos vinculados");
        }
        categoryRepository.delete(category);
    }

    private Category getEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Categoria", id));
    }
}
