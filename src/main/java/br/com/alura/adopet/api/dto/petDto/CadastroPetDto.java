package br.com.alura.adopet.api.dto.petDto;


import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroPetDto(@NotNull Long abrigoId,
                             @NotNull TipoPet tipo,
                             @NotBlank String nome,
                             @NotBlank String raca,
                             @NotNull Integer idade,
                             @NotBlank String cor,
                             @NotNull Float peso) {
}
