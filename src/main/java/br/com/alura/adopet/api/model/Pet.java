package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.petDto.CadastroPetDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoPet tipo;

    @NotBlank
    private String nome;

    @NotBlank
    private String raca;

    @NotNull
    private Integer idade;

    @NotBlank
    private String cor;

    @NotNull
    private Float peso;

    @NotNull
    private Boolean adotado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet")
    private Adocao adocao;


    public Pet(@Valid CadastroPetDto dto, Abrigo abrigo) {

        this.tipo = dto.tipo();
        this.nome = dto.nome();
        this.raca = dto.raca();
        this.idade = dto.idade();
        this.cor = dto.cor();
        this.peso = dto.peso();
        this.adotado = false;
        this.abrigo = abrigo;
    }

    public void marcarComoAdotado() {
        this.adotado = true;
    }
}
