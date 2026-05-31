package br.com.importaai.ecommercepetshop.mapper;

import br.com.importaai.ecommercepetshop.dto.request.CategoryRequest;
import br.com.importaai.ecommercepetshop.dto.response.CategoryResponse;
import br.com.importaai.ecommercepetshop.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }

    public void updateEntity(Category category, CategoryRequest request) {
        category.setName(request.name());
        category.setDescription(request.description());
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription());
    }
}
