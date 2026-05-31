package br.com.importaai.ecommercepetshop.dto.response;

public record AddressResponse(
        Long id,
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state,
        String zipCode
) {
}
