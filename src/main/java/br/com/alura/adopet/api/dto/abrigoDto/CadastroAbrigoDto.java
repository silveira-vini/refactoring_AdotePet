package br.com.alura.adopet.api.dto.abrigoDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroAbrigoDto(
        @NotBlank
        String nome,
        @NotBlank
        String telefone,
        @Email
        @NotBlank
        String email) {
}
