package br.com.alura.adopet.api.Validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import org.springframework.stereotype.Component;

@Component
public interface ValidacaoSolicitacaoAdocao {

    void validar(SolicitacaoAdocaoDto dto);

}
