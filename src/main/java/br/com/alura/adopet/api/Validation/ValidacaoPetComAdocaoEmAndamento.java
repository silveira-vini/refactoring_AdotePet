package br.com.alura.adopet.api.Validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    @Autowired
    AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {

        boolean petAguardandoAvaliacao = adocaoRepository.existsByPetIdAndStatus(dto.IdPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petAguardandoAvaliacao) {
            throw new ValidationException("Pet já está aguardando avaliação para ser adotado!");
        }

    }
}
