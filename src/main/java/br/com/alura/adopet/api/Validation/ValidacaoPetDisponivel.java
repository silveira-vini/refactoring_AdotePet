package br.com.alura.adopet.api.Validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

    @Autowired
    PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto dto) {

        boolean petAdotado = petRepository.existsByAdotadoTrueAndId(dto.IdPet());

        if (petAdotado) {
            throw new ValidationException("Pet já foi adotado!");
        }
    }
}
