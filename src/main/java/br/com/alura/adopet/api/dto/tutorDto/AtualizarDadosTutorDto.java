package br.com.alura.adopet.api.dto.tutorDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AtualizarDadosTutorDto(@NotNull
                                     Long id,
                                     String nome,
                                     String telefone,
                                     @Email
                                     String email) {
}
