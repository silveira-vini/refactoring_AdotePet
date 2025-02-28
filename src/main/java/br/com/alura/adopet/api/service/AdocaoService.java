package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.validation.ValidacaoSolicitacaoAdocao;
import br.com.alura.adopet.api.dto.adocaoDto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.DadosDetalhadosAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
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

    @Autowired
    private ListToJsonService listToJsonService;

    public void solicitar(SolicitacaoAdocaoDto dto) {

        validacoes.forEach(v -> v.validar(dto));

        var tutor = tutorRepository.getReferenceById(dto.IdTutor());
        var pet = petRepository.getReferenceById(dto.IdPet());

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        adocaoRepository.save(adocao);
        emailService.solicitar(adocao);
    }

    public String listar() {
        List<Adocao> adocoes = adocaoRepository.findAll();
        var listAdocoes = adocoes.stream()
                .map(a -> new DadosDetalhadosAdocaoDto(a.getId(),
                        a.getData().toString(),
                        a.getTutor().getNome(),
                        a.getPet().getNome(),
                        a.getMotivo(),
                        a.getStatus().toString(),
                        a.getJustificativaStatus() == null ? "-" : a.getJustificativaStatus()))
                .toList();
        return listToJsonService.convert(listAdocoes);
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
