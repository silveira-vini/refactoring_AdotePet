package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.tutorDto.CadastroTutorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tutores")
public class Tutor {

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

    @OneToMany(mappedBy = "tutor")
    private List<Adocao> adocoes;

    public Tutor(CadastroTutorDto tutorDto) {
        this.nome = tutorDto.nome();
        this.telefone = tutorDto.telefone();
        this.email = tutorDto.email();
    }
}
