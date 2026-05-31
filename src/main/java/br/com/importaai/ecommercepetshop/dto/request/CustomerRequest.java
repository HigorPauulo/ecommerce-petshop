package br.com.importaai.ecommercepetshop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CustomerRequest(

        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 150)
        String name,

        @NotBlank(message = "O e-mail e obrigatorio")
        @Email(message = "E-mail invalido")
        @Size(max = 150)
        String email,

        @NotBlank(message = "O CPF e obrigatorio")
        @Size(max = 14, message = "CPF deve ter no maximo 14 caracteres")
        String cpf,

        @Size(max = 20)
        String phone,

        @Valid
        List<AddressRequest> addresses
) {
}
