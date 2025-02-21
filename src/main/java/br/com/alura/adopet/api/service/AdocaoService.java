package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.Validation.ValidacaoSolicitacaoAdocao;
import br.com.alura.adopet.api.dto.adocaoDto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private List<ValidacaoSolicitacaoAdocao> validacoes;

    @Autowired
    private EmailService emailService;

    public void solicitar(SolicitacaoAdocaoDto dto) {

        validacoes.forEach(v -> v.validar(dto));

        var tutor = tutorRepository.getReferenceById(dto.IdTutor());
        var pet = petRepository.getReferenceById(dto.IdPet());

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        adocaoRepository.save(adocao);
        emailService.solicitar(adocao);
    }

    public void aprovar(AprovacaoAdocaoDto dto) {
        Adocao adocao = adocaoRepository.getReferenceById(dto.IdAdocao());
        adocao.marcaComoAprovado();
        emailService.aprovar(adocao);
    }

    public void reprovar(ReprovacaoAdocaoDto dto) {
        Adocao adocao = adocaoRepository.getReferenceById(dto.IdAdocao());
        adocao.marcaComoReprovado(dto.justificativa());
        emailService.reprovar(adocao);
    }
}
