package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ValidacaoPetDisponivelTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ValidacaoPetDisponivel validacaoPetDisponivel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {

        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo qualquer");

        //Simula que o pet não foi adotado
        when(petRepository.existsByAdotadoTrueAndId(1L)).thenReturn(false);

        //ACT + ASSERTION
        assertDoesNotThrow(() -> validacaoPetDisponivel.validar(dto));

    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {

        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo qualquer");

        //Simula que o pet foi já foi adotado
        when(petRepository.existsByAdotadoTrueAndId(1L)).thenReturn(true);

        //ACT + ASSERTION
        assertThrows(ValidationException.class, () -> validacaoPetDisponivel.validar(dto));
    }

}