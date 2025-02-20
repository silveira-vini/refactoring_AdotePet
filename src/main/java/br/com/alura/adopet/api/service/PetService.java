package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.DadosDetalhadosPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public ResponseEntity<List<DadosDetalhadosPetDto>> listarPetsDisponiveis() {

        List<Pet> pets = petRepository.findByAdotadoFalse();
        List<DadosDetalhadosPetDto> disponiveis = pets.stream().map(DadosDetalhadosPetDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(disponiveis);
    }

    public ResponseEntity<String> cadastrarPet(@Valid CadastroPetDto dto) {
        Pet pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso());
        petRepository.save(pet);
        return ResponseEntity.ok("Pet cadastrado com sucesso!");
    }
}
