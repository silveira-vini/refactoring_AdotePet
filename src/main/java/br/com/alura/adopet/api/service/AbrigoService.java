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

    @Autowired
    private ListToJsonService listToJsonService;

    public ResponseEntity<List<AbrigoDetalhadoDto>> listarAbrigos() {
        List<Abrigo> abrigos = abrigoRepository.findByAtivoTrue();
        var abrigosDetalhados = abrigos.stream().map(a -> new AbrigoDetalhadoDto(a.getNome(),
                a.getTelefone(), a.getEmail(), a.getId())).toList();
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
            return listarPetsDeAbrigoEncontrado(abrigoOptional);
        } catch (NumberFormatException e) {
            Optional<Abrigo> abrigoOptional = Optional.ofNullable(abrigoRepository.findByNome(idOuNome));
            return listarPetsDeAbrigoEncontrado(abrigoOptional);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pets do abrigo");
        }
    }

    private ResponseEntity<String> listarPetsDeAbrigoEncontrado(Optional<Abrigo> abrigoOptional) {
        if (abrigoOptional.isPresent()) {
            List<Pet> pets = abrigoOptional.get().getPets();
            var petsDetalhados = pets.stream().map(p -> new DadosDetalhadosPetDto(p.getId() ,p.getNome(),
                    p.getRaca(), p.getIdade(), p.getCor(), p.getPeso(),
                    p.getAbrigo().getNome())).toList();
            String body = listToJsonService.convert(petsDetalhados);
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(404).body("Abrigo não encontrado");
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


}

