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
    private static final String ARQUIVO_CONTADOR = "D:\\Projetos Intellij Java\\Projeto-cadastr-devMagro\\contador\\contador.txt";

    public void carregarPergunta(String arquivo){
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String item ;
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
        String diretorio = "D:\\Projetos Intellij Java\\Projeto-cadastr-devMagro\\usuarios";
        File pasta =  new File(diretorio);
        if (!pasta.exists()){
            pasta.mkdir();
        }

        String nomeArquivo = diretorio + "\\" + contador + "-" + usuario.getNome().replace(" ", "").toUpperCase() + ".txt";
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
        String diretorio = "D:\\Projetos Intellij Java\\Projeto-cadastr-devMagro\\contador";
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
}
