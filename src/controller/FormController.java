package controller;

import model.entity.Pergunta;
import model.entity.Usuario;
import view.Form;

import java.io.*;
import java.util.*;

public class FormController {
    Scanner sc = new Scanner(System.in);
    Form view = new Form();

    private List<Pergunta> perguntas = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private int contador = carregarContador();
    private static final String ARQUIVO_CONTADOR = "./contador/contador.txt";
    private final String CAMINHO_ARQUIVO_USUARIOS = "./usuarios";
    private final String CAMINHO_ARQUIVO_FORMULARIO = "./formulario.txt";

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void carregarPergunta() {
        perguntas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO_FORMULARIO))) {
            String item;
            while ((item = br.readLine()) != null) {
                String[] partes = item.split(" - ", 2);
                int numero = Integer.parseInt(partes[0]);
                String texto = partes[1];
                perguntas.add(new Pergunta(numero, texto));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void salvarUsuario(Usuario usuario) {
        File pasta = new File(CAMINHO_ARQUIVO_USUARIOS);
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        String nomeArquivo = CAMINHO_ARQUIVO_USUARIOS + "\\" + contador + "-" + usuario.getNome().replace(" ", "").toUpperCase() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {

            bw.write("Nome: " + usuario.getNome());
            bw.newLine();
            bw.write("Email: " + usuario.getEmail());
            bw.newLine();
            bw.write("Idade: " + usuario.getIdade());
            bw.newLine();
            bw.write("Altura: " + usuario.getAltura());
            bw.newLine();

            List<String> perguntasAdicionais = usuario.getPerguntaAdicional();
            if (perguntasAdicionais != null && !perguntasAdicionais.isEmpty()) {
                for (int i = 0; i < perguntasAdicionais.size(); i++) {
                    bw.write("Pergunta " + (i + 5) + ": " + perguntasAdicionais.get(i));
                    bw.newLine();
                }
            }

            System.out.println("Arquivo salvo com sucesso: " + nomeArquivo);
            contador++;
            salvarContador(contador);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int carregarContador() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CONTADOR))) {
            String linha = br.readLine();
            if (linha != null) {
                return Integer.parseInt(linha);
            } else {
                return 1;
            }
        } catch (IOException e) {
            return 1;
        }
    }

    private void salvarContador(int contador) {
        String diretorio = "./contador";
        File pasta = new File(diretorio);
        if (!pasta.exists()) {
            pasta.mkdir();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CONTADOR))) {
            bw.write(String.valueOf(contador));
        } catch (IOException e) {
            System.out.println("Erro no contador: " + e.getMessage());
        }
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public Usuario responderPerguntas() {
        List<String> respostas = new ArrayList<>();

        for (Pergunta pergunta : perguntas) {
            String resposta = sc.nextLine();
            respostas.add(resposta);
        }

        String nome = respostas.get(0);
        String email = respostas.get(1);
        Integer idade = Integer.parseInt(respostas.get(2));
        Double altura = Double.parseDouble(respostas.get(3).replace(",", "."));

        List<String> respostasAdicionar = respostas.size() > 4 ? respostas.subList(4, respostas.size()) : new ArrayList<>();

        return new Usuario(nome, idade, email, altura, respostasAdicionar);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void listarUsuarios() {
        File pastaUsuarios = new File(CAMINHO_ARQUIVO_USUARIOS);
        if (!pastaUsuarios.exists() || pastaUsuarios.listFiles() == null || pastaUsuarios.listFiles().length == 0) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        int contador = 1;
        for (File arquivo : pastaUsuarios.listFiles()) {
            if (arquivo.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha = br.readLine();
                    if (linha != null && linha.startsWith("Nome: ")) {
                        String nome = linha.replace("Nome: ", "");
                        System.out.println(contador + " - " + nome);
                        contador++;
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public void cadastrarPergunta() {
        System.out.println("Perguntas do formulario: ");
        carregarPergunta();
        view.exibirPerguntas(getPerguntas());

        System.out.println("\nDigite a pergunta a se adicionada: ");
        String perguntaAdiconada = sc.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO_FORMULARIO))) {
            int numeroPerguntas = 0;
            while (br.readLine() != null) {
                numeroPerguntas++;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO_FORMULARIO, true))) {
                bw.write((numeroPerguntas + 1) + " - " + perguntaAdiconada);
                bw.newLine();
            }

            System.out.println("Pergunta adicionada com sucesso!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deletarPergunta() {
        System.out.println("Perguntas do formulario: ");
        carregarPergunta();
        view.exibirPerguntas(getPerguntas());
        List<String> perguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO_FORMULARIO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                perguntas.add(linha);
            }
        } catch (IOException e ) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.print("\nDigite o número da pergunta a ser deletada(Você não pode deletar as 4 primeiras perguntas): ");
        int numero = sc.nextInt();
        sc.nextLine();

        if (numero <= 4) {
            System.out.println("Não é possível deletar as 4 primeiras perguntas.");
            return;
        }
        if (numero > perguntas.size()){
            System.out.println("Número inválido! Pergunta não existe.");
            return;
        }
        perguntas.remove(numero - 1);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO_FORMULARIO))){
            for (int i = 0; i < perguntas.size() ; i++) {
                String formularioAtualizado = (i + 1) + " - " + perguntas.get(i).split(" - ", 2)[1];
                bw.write(formularioAtualizado);
                bw.newLine();
            }
        } catch (IOException e ) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n Pergunta deletada com sucesso!");
    }

    public List<String> pesquisarUsuario(String nomeUsuario) {

        return usuarios.stream()
                .filter(usuario -> usuario.getNome().toUpperCase().contains(nomeUsuario))
                .map(Usuario::getNome)
                .sorted(String::compareToIgnoreCase)
                .toList();
    }
}
