package application;

import controller.FormController;
import model.entity.Usuario;
import view.Form;

import java.util.List;
import java.util.Scanner;

public class Principal {
    FormController formController = new FormController();
    Form view = new Form();


    Scanner sc = new Scanner(System.in);

    public void menu() {
        int opcao;
        do {
            System.out.println("""
                Digite uma das opções:
                1 - Cadastar o usuário.
                2 - Listar todos usuários cadastrados.
                3 - Cadastar nova pergunta no formulário.
                4 - Deletar pergunta do formulãrio.
                5 - Pesquisar usuário por nome.
                0 - Sair.
                """);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 0: System.out.println("Encerrando o programa...."); return;
                case 1: cadastrarUsuario(); break;
                case 2: listarUsuario(); break;
                case 3: cadastrarPergunta(); break;
                case 4: deletarPergunta(); break;
                case 5: pesquisarUsuario(); break;
                default: System.out.println("Opção inválida. ");
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario() {
        try {
            System.out.println("Responda ao formulário: ");
            formController.carregarPergunta();
            view.exibirPerguntas(formController.getPerguntas());

            Usuario usuario = formController.responderPerguntas();

            formController.adicionarUsuario(usuario);
            view.exibirUsuario(List.of(usuario));
            formController.salvarUsuario(usuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void listarUsuario() {
        formController.listarUsuarios();
    }
    private void cadastrarPergunta() {
        formController.cadastrarPergunta();
    }
    private void deletarPergunta() {
        formController.deletarPergunta();
    }
    private void pesquisarUsuario() {
        System.out.println("Digite o nome do usuario para buscar: ");
        String nomeUsuario = sc.nextLine().toUpperCase();
        List<String> listaUsuario = formController.pesquisarUsuario(nomeUsuario);

        if (listaUsuario.isEmpty()){
            System.out.println("Nenhum usuario encontrado.");
        } else {
            System.out.println("\nUsuarios encontrados: ");
            listaUsuario.forEach(System.out::println);
        }
    }
}
