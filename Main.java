import java.util.List;

import backend.*;
import frontend.*;

public class Main {
    public static void main(String[] args) {
        try {
            Manager db = Manager.getInstance();
            new XilftenMenu(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para exemplificar uso do backend
    void exemploBackend() {
        try {
            // Obter a instância do Manager, ele não pode ser instanciado diretamente
            // Isso é uma forma de garantir que apenas uma instância do Manager seja criada
            Manager db = Manager.getInstance();

            // Criar um novo filme
            Film filme = new Film("O nome do filme", 2021, "Ação", 120);

            // Salvar o filme no banco de dados
            db.save(filme);

            // Atualizar o filme no banco de dados
            // O metodo update pegará o filme pelo id e atualizará todos os campos
            // Se algum campo não for alterado, ele será mantido
            filme.setDuration(130);
            db.update(filme);

            // Deletar o filme do banco de dados
            // Para evitar erros, só é possível deletar um filme que já está no banco de
            // dados logo não há um metodo delete que recebe um id como parametro por
            // exemplo
            // Para deletar um filme pelo id, é necessário obter o filme pelo id e então
            // deletá-lo
            db.delete(filme);

            // Todos os metodos get retornam uma lista de Media, então é necessário fazer
            // um cast para o tipo correto

            // Obter todos os filmes com o id 1, como o id é único, a lista terá apenas um
            // filme se existir se não existir, a lista estará vazia
            Film filme2 = (Film) db.get(Film.TABLE_NAME, 1).get(0);

            // Obter todos os filmes com o título "O nome do filme", como o título não é
            // único, a lista pode ter mais de um filme ou nenhum
            Film filme3 = (Film) db.get(Film.TABLE_NAME, "O nome do filme").get(0);

            // Obter todos os filmes que tem "nome" no título, como o título não é único, a
            // lista pode ter mais de um filme ou nenhum
            Film filme4 = (Film) db.getFuzzy(Film.TABLE_NAME, "nome").get(0);

            // Obter todos os filmes
            List<Media> filmes = db.get(Film.TABLE_NAME);

            // Tudo isso também se aplica da mesma forma para Series e Documentarios por
            // serem subclasses de Media
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}