package br.com.alura.adopet.api.dto.tutorDto;

import jakarta.validation.constraints.NotBlank;

public record CadastroTutorDto(@NotBlank String nome,
                               @NotBlank String telefone,
                               @NotBlank String email) {
}
