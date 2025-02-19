package br.com.alura.adopet.api.Validation;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorLimiteAdocoes {

    @Autowired
    AdocaoRepository adocaoRepository;

    @Autowired
    TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto) {

        Tutor tutor = tutorRepository.getReferenceById(dto.IdTutor());
        List<Adocao> adocoes = adocaoRepository.findAll();

        for (Adocao a : adocoes) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                contador++;
            }
            if (contador == 5) {
                throw new ValidationException("Tutor chegou ao limite máximo de 5 adoções!");
            }
        }
    }
}
