package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutorDto.AtualizarDadosTutorDto;
import br.com.alura.adopet.api.dto.tutorDto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroTutorDto tutorDto) {
        return tutorService.cadastrar(tutorDto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarDadosTutorDto tutorDto) {
        return tutorService.atualizar(tutorDto);
    }

    @GetMapping
    public ResponseEntity<String> listar() {
        return tutorService.listar();
    }
}
