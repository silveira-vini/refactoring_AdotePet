package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdocaoServiceTest {


    private AdocaoService adocaoService;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private ValidacaoSolicitacaoAdocao validacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adocaoService = new AdocaoService();
        adocaoService.setAdocaoRepository(adocaoRepository);
        adocaoService.setPetRepository(petRepository);
        adocaoService.setTutorRepository(tutorRepository);
        adocaoService.setEmailService(emailService);
        adocaoService.setValidacoes(Collections.singletonList(validacao));
    }

    @Test
    void deveriaSalvarAdocaoQuandoSolicitacaoValida() {
        // Arrange
        Long idTutor = 1L;
        Long idPet = 1L;
        String motivo = "Motivo qualquer";

        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(idPet, idTutor, motivo);

        Tutor tutor = tutorRepository.getReferenceById(idTutor);
        Pet pet = petRepository.getReferenceById(idPet);

        when(tutorRepository.getReferenceById(dto.IdTutor())).thenReturn(tutor);
        when(petRepository.getReferenceById(dto.IdPet())).thenReturn(pet);

        // Act
        adocaoService.solicitar(dto);

        // Assert
        ArgumentCaptor<Adocao> adocaoCaptor = ArgumentCaptor.forClass(Adocao.class);
        verify(adocaoRepository).save(adocaoCaptor.capture());
        verify(emailService).solicitar(any(Adocao.class));

        Adocao adocao = adocaoCaptor.getValue();
        assertEquals(tutor, adocao.getTutor());
        assertEquals(pet, adocao.getPet());
        assertEquals(motivo, adocao.getMotivo());
    }
}