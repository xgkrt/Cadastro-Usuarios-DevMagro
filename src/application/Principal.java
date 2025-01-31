package application;

import controller.FormController;
import model.entity.Usuario;
import view.Form;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    private final String CAMINHO_ARQUIVO = "D:\\Projetos Intellij Java\\Projeto-cadastr-devMagro\\formulario.txt";

    public String getCAMINHO_ARQUIVO() {
        return CAMINHO_ARQUIVO;
    }

    public void Principal(){
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        FormController formController = new FormController();
        Form view = new Form();

        try{
            System.out.println("Responda ao formulário: ");
            formController.carregarPergunta(CAMINHO_ARQUIVO);
            view.exibirPerguntas(formController.getPerguntas());

            Usuario usuario = formController.responderPerguntas();

            formController.adicionarUsuario(usuario);
            view.exibirUsuario(List.of(usuario));
            formController.salvarUsuario(usuario);

        }catch (Exception e) {
            e.printStackTrace();
        }



    }
}
