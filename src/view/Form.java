package view;

import model.entity.Pergunta;
import model.entity.Usuario;

import java.util.List;

public class Form {
    public void exibirPerguntas(List<Pergunta> perguntas){
        for (Pergunta pergunta : perguntas) {
            System.out.println(pergunta);
        }
    }

    public void exibirUsuario(List<Usuario> usuarios){
        System.out.println("\nInformações do usuario: ");
        for(Usuario usuario : usuarios){
            System.out.println(usuarios);
        }
    }
}
