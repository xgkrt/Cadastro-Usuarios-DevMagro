package model.entity;

public class Usuario extends Pessoa{
    private String email;
    private Double altura;

    public Usuario(String nome, Integer idade, String email, Double altura) {
        super(nome, idade);
        this.email = email;
        this.altura = altura;
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


    @Override
    public String toString() {
        return "Nome: " +
                getNome() +
                "\nEmail: " +
                email +
                "\nIdade: " +
                getIdade() +
                "\nAltura: " +
                getAltura();
    }
}
