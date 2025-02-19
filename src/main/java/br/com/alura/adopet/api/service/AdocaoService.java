package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;

    public void solicitar(SolicitacaoAdocaoDto dto) {

        Pet pet = petRepository.getReferenceById(dto.IdPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.IdTutor());




        Adocao adocao = new Adocao();
        adocao.setPet(petRepository.getReferenceById(dto.IdPet()));
        adocao.setTutor(tutorRepository.getReferenceById(dto.IdTutor()));
        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        adocaoRepository.save(adocao);
        emailService.solicitar(adocao);
    }

    public void aprovar(AprovacaoAdocaoDto dto) {
        Adocao adocao = adocaoRepository.getReferenceById(dto.IdAdocao());
        adocao.setStatus(StatusAdocao.APROVADO);
        emailService.aprovar(adocao);
    }

    public void reprovar(ReprovacaoAdocaoDto dto) {
        Adocao adocao = adocaoRepository.getReferenceById(dto.IdAdocao());
        adocao.setStatus(StatusAdocao.REPROVADO);
        adocao.setMotivo(dto.motivo());
        emailService.reprovar(adocao);
    }
}
