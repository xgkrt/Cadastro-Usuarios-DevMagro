package model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario extends Pessoa{
    private String email;
    private Double altura;
    private List<String> perguntaAdicional = Collections.emptyList();

    public Usuario(String nome, Integer idade, String email, Double altura) {
        this(nome, idade, email, altura, null);
    }

    public Usuario(String nome, Integer idade, String email, Double altura, List<String> perguntaAdicional) {
        super(nome, idade);
        this.email = email;
        this.altura = altura;
        this.perguntaAdicional = perguntaAdicional != null ? perguntaAdicional : new ArrayList<>();
    }

    public Usuario(List<String> dadosUsuario) {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public List<String> getPerguntaAdicional() {
        return perguntaAdicional;
    }

    public void setPerguntaAdicional(List<String> perguntaAdicional) {
        this.perguntaAdicional = perguntaAdicional;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(getNome()).append("\n");
        sb.append("Email: ").append(getEmail()).append("\n");
        sb.append("Idade: ").append(getIdade()).append("\n");
        sb.append("Altura: ").append(getAltura()).append("\n");

        if (perguntaAdicional != null && !perguntaAdicional.isEmpty()){
            sb.append("Perguntas adicionais:\n");
            for (int i = 0; i < perguntaAdicional.size(); i++) {
                sb.append("Pergunta ").append(i + 5).append(": ").append(perguntaAdicional.get(i)).append("\n");
            }
        }

        return sb.toString();
    }
}
