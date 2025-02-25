package br.com.alura.adopet.api.dto.adocaoDto;

public record DadosDetalhadosAdocaoDto(Long id,
                                       String data,
                                       String tutor,
                                       String pet,
                                       String motivo,
                                       String status,
                                       String justificativaStatus) {
}
