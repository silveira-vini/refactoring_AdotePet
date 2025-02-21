package br.com.alura.adopet.api.dto.adocaoDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReprovacaoAdocaoDto(@NotNull Long IdAdocao,
                                  @NotBlank String justificativa) {
}
