package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhadosPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @GetMapping
    public ResponseEntity<List<DadosDetalhadosPetDto>> listarTodosDisponiveis() {
        List<Pet> pets = repository.findAll();
        List<DadosDetalhadosPetDto> disponiveis = pets.stream().filter(pet -> !pet.getAdotado())
                .map(DadosDetalhadosPetDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(disponiveis);
    }
}
