package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDetalhadoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    public ResponseEntity<List<AbrigoDetalhadoDto>> listarAbrigos() {
        List<Abrigo> abrigos = abrigoRepository.findByAtivoTrue();
        var abrigosDetalhados = abrigos.stream().map(a -> new AbrigoDetalhadoDto(a.getNome(),
                a.getTelefone(), a.getEmail())).toList();
        return ResponseEntity.ok(abrigosDetalhados);
    }

    public ResponseEntity<String> cadastrarAbrigo(@Valid Abrigo abrigo) {
        boolean nomeJaCadastrado = abrigoRepository.existsByNome(abrigo.getNome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(abrigo.getTelefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(abrigo.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro abrigo!");
        } else {
            abrigoRepository.save(abrigo);
            return ResponseEntity.ok("Abrigo Cadastrado com sucesso");
        }
    }

    public ResponseEntity<List<Pet>> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
            return ResponseEntity.ok(pets);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();
                return ResponseEntity.ok(pets);
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    public ResponseEntity cadastrarPet(String idOuNome, @Valid Pet pet) {
        Optional<Abrigo> abrigoOptional = buscarAbrigoPorIdOuNome(idOuNome);
        if (abrigoOptional.isPresent()) {
            return salvarPetNoAbrigo(pet, abrigoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    private Optional<Abrigo> buscarAbrigoPorIdOuNome(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            return Optional.of(abrigoRepository.getReferenceById(id));
        } catch (NumberFormatException e) {
            return Optional.ofNullable(abrigoRepository.findByNome(idOuNome));
        } catch (EntityNotFoundException enfe) {
            return Optional.empty();
        }
    }

    private ResponseEntity<String> salvarPetNoAbrigo(Pet pet, Abrigo abrigo) {
        pet.setAbrigo(abrigo);
        abrigo.getPets().add(pet);
        abrigoRepository.save(abrigo);
        return ResponseEntity.ok("Pet cadastrado no abrigo com sucesso");
    }
}

