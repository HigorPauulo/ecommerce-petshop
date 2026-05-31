package br.com.importaai.ecommercepetshop.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "O nome do produto e obrigatorio")
        @Size(max = 150, message = "O nome deve ter no maximo 150 caracteres")
        String name,

        @Size(max = 1000, message = "A descricao deve ter no maximo 1000 caracteres")
        String description,

        @NotNull(message = "O preco e obrigatorio")
        @Digits(integer = 8, fraction = 2, message = "Preco invalido")
        @PositiveOrZero(message = "O preco nao pode ser negativo")
        BigDecimal price,

        @NotNull(message = "A quantidade em estoque e obrigatoria")
        @PositiveOrZero(message = "O estoque nao pode ser negativo")
        Integer stockQuantity,

        @Size(max = 500, message = "A URL da imagem deve ter no maximo 500 caracteres")
        String imageUrl,

        Boolean active,

        @NotNull(message = "A categoria e obrigatoria")
        Long categoryId
) {
}
