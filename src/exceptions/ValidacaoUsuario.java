package exceptions;

import model.entity.Usuario;

import java.util.List;

public class ValidacaoUsuario {

    public void validarUsuario(String nome, String email, int idade, String alturaStr, List<Usuario> usuarios) throws ValidacaoException {
        if (nome == null || nome.length() < 10) {
            throw new ValidacaoException("O nome deve ter pelo menos 10 caracteres!");
        }

        if (email == null || !email.contains("@")) {
            throw new ValidacaoException("Email deve conter o @");
        }

        if (idade < 18) {
            throw new ValidacaoException("O usuário deve ser maior de 18 anos");
        }

        if (alturaStr == null || !alturaStr.contains(",")){
            throw new ValidacaoException("A altura deve ser infomada com ','");
        }

        for (Usuario u : usuarios){
            if (u.getEmail().equalsIgnoreCase(email)){
                throw new ValidacaoException("O email ja está sendo usado!");
            }
        }
    }

    public void deletarPergunta(int numero, int totalPerguntas) throws ValidacaoException {
        if (numero <= 4) {
            throw new ValidacaoException("Não é possível deletar as 4 primeiras perguntas.");
        }
        if (numero > totalPerguntas) {
            throw new ValidacaoException("Número inválido! Pergunta não existe.");
        }
    }
}
