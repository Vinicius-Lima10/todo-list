import java.time.LocalDate;
import java.util.ArrayList;

public class TaskController {
    ArrayList<Tarefa> listaTarefa = new ArrayList<>();
    public TaskController() {

    }
    public void criarTarefa(String nome, String descricao,
                            int nivelPrioridade, String categoria,
                            Status status, LocalDate dataDeTermino) {
        listaTarefa.stream().forEach(tarefa ->{
            if(tarefa.getNome().equalsIgnoreCase(nome) ) {
                throw new RuntimeException("Tarefa já existente de nome: " + nome);
            }
        });
        listaTarefa.add(new  Tarefa(nome, descricao, nivelPrioridade,
                categoria, status, dataDeTermino));
    }
    public void deletarTarefa(String nome) {

    }
}
