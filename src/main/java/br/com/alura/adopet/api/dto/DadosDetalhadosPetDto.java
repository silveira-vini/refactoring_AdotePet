package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;

public record DadosDetalhadosPetDto(Long id, String nome, String raca, Integer idade, String cor, Float peso) {

    public DadosDetalhadosPetDto (Pet pet) {
        this(pet.getId(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso());
    }
}
