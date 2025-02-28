package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorLimiteAdocoes implements ValidacaoSolicitacaoAdocao {

    @Autowired
    AdocaoRepository adocaoRepository;

    @Autowired
    TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto) {

        long contagem = adocaoRepository.countByTutorIdAndStatus(dto.IdTutor(), StatusAdocao.APROVADO);

        if(contagem >= 5){
            throw new ValidationException("Tutor já possui 5 adoções efetivadas");
        }
    }
}
