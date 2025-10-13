import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    private ArrayList<Tarefa> listaTarefa = new ArrayList<>();

    public void criarTarefa(String nome, String descricao,
                            int nivelPrioridade, String categoria,
                            Status status, LocalDate dataDeTermino) {

        //Primeiro verifica se não tem outra com mesmo nome
        boolean existe = listaTarefa.stream()
                .anyMatch(tarefa -> tarefa.getNome().equalsIgnoreCase(nome));

        if (existe) {
            throw new RuntimeException("Tarefa já existente de nome: " + nome);
        }

        //Adiciona a tarefa se não existe
        listaTarefa.add(new  Tarefa(nome, descricao, nivelPrioridade,
                categoria, status, dataDeTermino));

        //Ordena pela prioridade
        listaTarefa.sort((t1, t2) -> listaSort(t1, t2));
    }

    public List<Tarefa> listarTodasTarefas() {
        return this.listaTarefa;
    }


    //Busca por uma tarefa específica
    public Tarefa listarTarefa(String nome) {
        for(Tarefa tarefa: listaTarefa) {
            if(tarefa.getNome().equalsIgnoreCase(nome)) {
                return tarefa;
            }
        }
        return null;
    }

    //Deleta tarefa
    public void deletarTarefa(String nome) {
        boolean remover = this.listaTarefa.removeIf(tarefa -> {
            if(tarefa.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
            return false;
        });
        if(!remover) {
            System.out.println("Tarefa não  encontrada");
        }
    }

    //Editar taraefa
    public void editarTarefa(String nome, String novoNome,
                             String novaDescricao, Integer novaPrioridade,
                             String novaCategoria, Status novoStatus,
                             LocalDate novaData) {

        //Obter a tarefa específica que queremos editar
        Tarefa tarefa = listarTarefa(nome);
        if (tarefa == null) {
            throw new RuntimeException("Tarefa não encontrada - " + nome);
        }

        if (novoNome != null && !tarefa.getNome().equalsIgnoreCase(novoNome)) {
            //Se modifica o nome, pesquisa para ver se não tem nenhum igual
            boolean existe = listaTarefa.stream()
                    .anyMatch(t -> t.getNome().equalsIgnoreCase(novoNome));
            if (existe) {
                throw new RuntimeException("Já existe uma tarefa com o nome: " + novoNome);
            }
            tarefa.setNome(novoNome);
        }

        //Se alterar os campos, setamos os novos valores
        if (novaDescricao != null) tarefa.setDescricao(novaDescricao);
        if (novaPrioridade != null) tarefa.setNivelPrioridade(novaPrioridade);
        if (novaCategoria != null) tarefa.setCategoria(novaCategoria);
        if (novoStatus != null) tarefa.setStatus(novoStatus);
        if (novaData != null) tarefa.setDataDeTermino(novaData);

        listaTarefa.sort((t1, t2) -> listaSort(t1, t2));
    }
    public List<Tarefa> filtrarPorCategoria(String categoria) {

        return listaTarefa.stream()
                .filter(t -> t.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

    public List<Tarefa> filtrarPorStatus(Status status) {
        return listaTarefa.stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }

    public List<Tarefa> filtrarPorPrioridade(int prioridade) {
        return listaTarefa.stream()
                .filter(t -> t.getNivelPrioridade() == prioridade)
                .toList();
    }
    public int listaSort(Tarefa t1, Tarefa t2) {
        if (t1.getNivelPrioridade() > t2.getNivelPrioridade()) {
            return -1; // t1 vem antes de t2
        } else if (t1.getNivelPrioridade() < t2.getNivelPrioridade()) {
            return 1;  // t2 vem antes de t1
        } else {
            return 0;  // iguais
        }
    }

}