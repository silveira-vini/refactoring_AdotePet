package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.petDto.CadastroPetDto;
import br.com.alura.adopet.api.dto.petDto.DadosDetalhadosPetDto;
import br.com.alura.adopet.api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    PetService petService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhadosPetDto>> listarTodosDisponiveis() {
        return petService.listarPetsDisponiveis();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPet(@RequestBody @Valid CadastroPetDto dto) {
        return petService.cadastrarPet(dto);
    }
}
