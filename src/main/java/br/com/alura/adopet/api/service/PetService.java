package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.petDto.CadastroPetDto;
import br.com.alura.adopet.api.dto.petDto.DadosDetalhadosPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    AbrigoRepository abrigoRepository;

    public ResponseEntity<List<DadosDetalhadosPetDto>> listarPetsDisponiveis() {

        List<Pet> pets = petRepository.findAllByAdotadoFalse();
        List<DadosDetalhadosPetDto> disponiveis = pets.stream()
                .map(p -> new DadosDetalhadosPetDto(p.getId() ,p.getNome(),
                        p.getRaca(), p.getIdade(), p.getCor(), p.getPeso(),
                        p.getAbrigo().getNome()))
                .toList();
        return ResponseEntity.ok(disponiveis);
    }

    public ResponseEntity<String> cadastrarPet(@Valid CadastroPetDto dto) {
        Optional<Abrigo> abrigoOptional = abrigoRepository.findById(dto.abrigoId());
        if (abrigoOptional.isPresent()) {
        petRepository.save(new Pet(dto, abrigoOptional.get()));
        return ResponseEntity.ok("Pet cadastrado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Abrigo não encontrado!");
        }
    }
}
