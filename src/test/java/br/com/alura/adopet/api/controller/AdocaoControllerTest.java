package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class AdocaoControllerTest {

    @InjectMocks
    private AdocaoController adocaoController;

    @Mock
    private AdocaoService adocaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void solicitar_deveRetornarSucesso() {
        // Arrange
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo da adoção");

        // Act
        ResponseEntity<String> response = adocaoController.solicitar(dto);

        // Assert
        assertEquals(ResponseEntity.ok("Adoção Cadastrada com sucesso"), response);
        verify(adocaoService).solicitar(dto);
    }

    @Test
    void solicitar_deveRetornarErroQuandoExcecaoOcorre() {
        // Arrange
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo da adoção");
        doThrow(new RuntimeException("Erro ao cadastrar adoção")).when(adocaoService).solicitar(dto);

        // Act
        ResponseEntity<String> response = adocaoController.solicitar(dto);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Erro ao cadastrar adoção"), response);
    }
}