package br.com.importaai.ecommercepetshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "A rua e obrigatoria")
        @Size(max = 200)
        String street,

        @NotBlank(message = "O numero e obrigatorio")
        @Size(max = 20)
        String number,

        @Size(max = 100)
        String complement,

        @NotBlank(message = "O bairro e obrigatorio")
        @Size(max = 100)
        String district,

        @NotBlank(message = "A cidade e obrigatoria")
        @Size(max = 100)
        String city,

        @NotBlank(message = "O estado (UF) e obrigatorio")
        @Size(min = 2, max = 2, message = "A UF deve ter 2 letras")
        String state,

        @NotBlank(message = "O CEP e obrigatorio")
        @Size(max = 9)
        String zipCode
) {
}
