package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutorDto.AtualizarDadosTutorDto;
import br.com.alura.adopet.api.dto.tutorDto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public ResponseEntity<String> cadastrar(CadastroTutorDto tutorDto) {

        if (verificarAbrigoExistente(tutorDto)) {
            return ResponseEntity.badRequest().body("Tutor já cadastrado no banco de dados");
        }
        Tutor tutor = new Tutor(tutorDto.nome(), tutorDto.email(), tutorDto.telefone());
        tutorRepository.save(tutor);
        return ResponseEntity.ok("Tutor cadastrado com sucesso");
    }

    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarDadosTutorDto tutorDto) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(tutorDto.id());
        if (tutorOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Tutor não encontrado no banco de dados");
        }
        atualizaDadosTutor(tutorOptional, tutorDto);
        return ResponseEntity.ok("Tutor atualizado com sucesso");
    }

    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(tutorRepository.findAll().toString());
    }


    private boolean verificarAbrigoExistente(CadastroTutorDto tutorDto) {
        boolean telefoneJaCadastrado = tutorRepository.existsByTelefone(tutorDto.telefone());
        boolean emailJaCadastrado = tutorRepository.existsByEmail(tutorDto.email());
        return telefoneJaCadastrado || emailJaCadastrado;
    }

    private void atualizaDadosTutor(Optional<Tutor> tutorOptional, AtualizarDadosTutorDto tutorDto) {
        Tutor tutor = tutorOptional.get();
        if (tutorDto.nome() != null) {
            tutor.setNome(tutorDto.nome());
        }
        if (tutorDto.email() != null) {
            tutor.setEmail(tutorDto.email());
        }
        if (tutorDto.telefone() != null) {
            tutor.setTelefone(tutorDto.telefone());
        }
        tutorRepository.save(tutor);
    }
}
