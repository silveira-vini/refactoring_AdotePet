package br.com.alura.adopet.api.dto.petDto;

public record DadosDetalhadosPetDto(Long id,
                                    String nome,
                                    String raca,
                                    Integer idade,
                                    String cor,
                                    Float peso,
                                    String abrigo) {

}
