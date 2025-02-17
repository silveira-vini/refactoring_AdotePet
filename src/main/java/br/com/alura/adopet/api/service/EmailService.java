package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Adocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void solicitar(Adocao adocao) {
        String subject = "Solicitação de adoção";
        String text = "Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: "
                + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.";
        enviarEmail(adocao, subject, text);
    }

    public void aprovar(Adocao adocao) {
        String subject = "Adoção aprovada";
        String text = "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome()
                + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome()
                + " para agendar a busca do seu pet.";
        enviarEmail(adocao, subject, text);
    }

    public void reprovar(Adocao adocao) {
        String subject = "Adoção reprovada";
        String text = "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome()
                + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: "
                + adocao.getJustificativaStatus();
        enviarEmail(adocao, subject, text);
    }

    private void enviarEmail(Adocao adocao, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getPet().getAbrigo().getEmail());
        email.setSubject(subject);
        email.setText(text);
        emailSender.send(email);
    }
}