package controller;

import model.entity.Pergunta;
import model.entity.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FormController {
    Scanner sc = new Scanner(System.in);

    private List<Pergunta> perguntas = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private int contador = carregarContador();
    private static final String ARQUIVO_CONTADOR = "./contador/contador.txt";
    private final String CAMINHO_ARQUIVO_USUARIOS = "./usuarios";
    private final String CAMINHO_ARQUIVO_FORMULARIO = "./formulario.txt";

    public void carregarPergunta(){
        perguntas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO_FORMULARIO))) {
            String item;
            while ((item = br.readLine()) != null){
                String[] partes =  item.split(" - ", 2);
                int numero = Integer.parseInt(partes[0]);
                String texto = partes[1];
                perguntas.add(new Pergunta(numero, texto));
            }
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void salvarUsuario(Usuario usuario){
        File pasta =  new File(CAMINHO_ARQUIVO_USUARIOS);
        if (!pasta.exists()){
            pasta.mkdir();
        }

        String nomeArquivo = CAMINHO_ARQUIVO_USUARIOS + "\\" + contador + "-" + usuario.getNome().replace(" ", "").toUpperCase() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))){

                bw.write("Nome: " + usuario.getNome());
                bw.newLine();
                bw.write("Email: " + usuario.getEmail());
                bw.newLine();
                bw.write("Idade: " + usuario.getIdade());
                bw.newLine();
                bw.write("Altura: " + usuario.getAltura());

            System.out.println("Arquivo salvo com sucesso: " + nomeArquivo);
            contador++;
            salvarContador(contador);
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int carregarContador() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CONTADOR))){
            String linha = br.readLine();
            if (linha != null){
                return Integer.parseInt(linha);
            } else {
                return 1;
            }
        }catch (IOException e){
            return 1;
        }
    }

    private void salvarContador(int contador){
        String diretorio = "./contador";
        File pasta =  new File(diretorio);
        if (!pasta.exists()){
            pasta.mkdir();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CONTADOR))){
            bw.write(String.valueOf(contador));

        } catch (IOException e){
            System.out.println("Erro no contador: " + e.getMessage());
        }
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public Usuario responderPerguntas(){
        try {
            var nome = sc.nextLine();
            var email = sc.nextLine();
            var idade = sc.nextInt();
            sc.nextLine();
            double altura = Double.parseDouble(sc.nextLine().replace(",","."));
            return new Usuario(nome, idade, email, altura);
        } catch (Exception e){
            System.out.println("Entrada inválida. Tente novamente.");
            sc.nextLine();
            return responderPerguntas();
        }
    }

    public void adicionarUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public void listarUsuarios(){
        File pastaUsuarios = new File(CAMINHO_ARQUIVO_USUARIOS);
        if (!pastaUsuarios.exists() || pastaUsuarios.listFiles() == null || pastaUsuarios.listFiles().length == 0) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        int contador = 1;
        for (File arquivo : pastaUsuarios.listFiles()){
            if (arquivo.isFile()){
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
                    String linha = br.readLine();
                    if (linha != null && linha.startsWith("Nome: ")){
                        String nome = linha.replace("Nome: ", "");
                        System.out.println(contador + " - " + nome);
                        contador++;
                    }
                } catch (IOException e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public void cadastrarPergunta() {
        System.out.println("Digite a pergunta a se adicionada: ");
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
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deletarPergunta() {
        System.out.print("Digite o número da pergunta a ser deletada(Você não pode deletar as 4 primeiras perguntas): ");
        int numero = sc.nextInt();

        if (numero <= 4){
            System.out.println("Não é possivel deletar as 4 primeiras perguntas.");
            return;
        }


    }
}
