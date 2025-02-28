package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorAguardandoAvaliacao implements ValidacaoSolicitacaoAdocao {

    @Autowired
    AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {

        boolean tutorPossuiAdocaoAguardandoAvaliacao = adocaoRepository.existsByTutorIdAndStatus(dto.IdTutor()
                , StatusAdocao.AGUARDANDO_AVALIACAO);

        if (tutorPossuiAdocaoAguardandoAvaliacao) {
            throw new ValidationException("Tutor já possui outra adoção aguardando avaliação!");
        }
    }
}
