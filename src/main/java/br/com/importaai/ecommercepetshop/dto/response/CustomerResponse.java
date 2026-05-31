package br.com.importaai.ecommercepetshop.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String cpf,
        String phone,
        LocalDateTime createdAt,
        List<AddressResponse> addresses
) {
}
