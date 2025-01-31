package model.entity;

public class Pergunta {
    private int numero;
    private String texto;

    public Pergunta(int numero, String texto) {
        this.numero = numero;
        this.texto = texto;
    }

    public int getNumero() {
        return numero;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return numero +
                " - " +
                texto;
    }
}
