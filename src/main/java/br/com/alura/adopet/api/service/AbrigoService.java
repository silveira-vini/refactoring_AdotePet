package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigoDto.AbrigoDetalhadoDto;
import br.com.alura.adopet.api.dto.abrigoDto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.petDto.DadosDetalhadosPetDto;
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

    public ResponseEntity<String> cadastrarAbrigo(@Valid CadastroAbrigoDto abrigoDto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email());
        if (jaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro abrigo!");
        } else {
            abrigoRepository.save(new Abrigo(abrigoDto));
            return ResponseEntity.ok("Abrigo Cadastrado com sucesso");
        }
    }

    public ResponseEntity<String> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            Optional<Abrigo> abrigoOptional = abrigoRepository.findById(id);
            return listarPetsDeAbrigoPresente(abrigoOptional);
        } catch (NumberFormatException e) {
            Optional<Abrigo> abrigoOptional = Optional.ofNullable(abrigoRepository.findByNome(idOuNome));
            return listarPetsDeAbrigoPresente(abrigoOptional);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> cadastrarPet(String idOuNome, @Valid Pet pet) {
        Optional<Abrigo> abrigoOptional = buscarAbrigoPorIdOuNome(idOuNome);
        if (abrigoOptional.isPresent()) {
            Abrigo abrigo = abrigoOptional.get();
            pet.setAbrigo(abrigo); // Associa o pet ao abrigo
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
            return ResponseEntity.ok("Pet cadastrado no abrigo com sucesso");
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

    private ResponseEntity<String> listarPetsDeAbrigoPresente(Optional<Abrigo> abrigoOptional) {
        if (abrigoOptional.isPresent()) {
            List<Pet> pets = abrigoOptional.get().getPets();
            var petsDetalhados = pets.stream().map(DadosDetalhadosPetDto::new).toList();
            return ResponseEntity.ok(petsDetalhados.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

