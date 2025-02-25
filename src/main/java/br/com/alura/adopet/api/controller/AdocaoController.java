package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.adocaoDto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.DadosDetalhadosAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDto dto) {
        try {
            this.adocaoService.solicitar(dto);
            return ResponseEntity.ok("Adoção Cadastrada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> listar() {
        String adocoes = this.adocaoService.listar();
        return ResponseEntity.ok().body(adocoes);
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto dto) {
        this.adocaoService.aprovar(dto);
        return ResponseEntity.ok("Adoção aprovada com sucesso");
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto dto) {
        this.adocaoService.reprovar(dto);
        return ResponseEntity.ok("Adoção Reprovada com sucesso");
    }
}
