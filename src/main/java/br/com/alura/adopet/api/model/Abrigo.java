package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.abrigoDto.CadastroAbrigoDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "abrigos")
public class Abrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
    private String telefone;

    @NotBlank
    @Email
    private String email;

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL)
    @JsonManagedReference("abrigo_pets")
    private List<Pet> pets;

    @NotNull
    private boolean ativo;

    public Abrigo(@Valid CadastroAbrigoDto abrigoDto) {
        this.nome = abrigoDto.nome();
        this.telefone = abrigoDto.telefone();
        this.email = abrigoDto.email();
        this.ativo = true;
    }
}
