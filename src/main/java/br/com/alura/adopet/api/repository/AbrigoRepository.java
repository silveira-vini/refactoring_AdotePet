package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Abrigo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {

    Abrigo findByNome(String nome);

    List<Abrigo> findByAtivoTrue();

    boolean existsByNomeOrTelefoneOrEmail(@NotBlank String nome, @NotBlank String telefone, @Email @NotBlank String email);
}
