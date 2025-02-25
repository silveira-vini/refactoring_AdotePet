package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigoDto.AbrigoDetalhadoDto;
import br.com.alura.adopet.api.dto.abrigoDto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.EmailValidationException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;
    @Autowired
    private EmailValidationException validacaoEmail;

    @GetMapping
    public ResponseEntity<List<AbrigoDetalhadoDto>> listar() {
        return abrigoService.listarAbrigos();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto abrigoDto) {
        return abrigoService.cadastrarAbrigo(abrigoDto);
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<String> listarPets(@PathVariable String idOuNome){
        return abrigoService.listarPets(idOuNome);
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
        return abrigoService.cadastrarPet(idOuNome, pet);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        validacaoEmail.validar(ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
