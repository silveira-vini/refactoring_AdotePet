package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.petDto.CadastroPetDto;
import br.com.alura.adopet.api.dto.petDto.DadosDetalhadosPetDto;
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

        List<Pet> pets = petRepository.findAllByAdotadoFalse();
        List<DadosDetalhadosPetDto> disponiveis = pets.stream()
                .map(DadosDetalhadosPetDto::new)
                .toList();
        return ResponseEntity.ok(disponiveis);
    }

    public ResponseEntity<String> cadastrarPet(@Valid CadastroPetDto dto) {
        petRepository.save(new Pet(dto));
        return ResponseEntity.ok("Pet cadastrado com sucesso!");
    }
}
