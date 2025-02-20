package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
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

    public Pet(@NotNull TipoPet tipo,
               @NotBlank String nome,
               @NotBlank String raca,
               @NotNull Integer idade,
               @NotBlank String cor,
               @NotNull Float peso) {
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
        this.adotado = false;
    }

    public void marcarComoAdotado() {
        this.adotado = true;
    }
}
