import java.time.LocalDate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class TaskController {
    private ArrayList<Tarefa> listaTarefa = new ArrayList<>();


    public void criarTarefa(String nome, String descricao,
                            int nivelPrioridade, String categoria,
                            Status status, LocalDate dataDeTermino,
                            boolean alarme) {

        boolean existe = listaTarefa.stream()
                .anyMatch(tarefa -> tarefa.getNome().equalsIgnoreCase(nome));

        if (existe) {
            throw new RuntimeException("Tarefa já existente: " + nome);
        }

        listaTarefa.add(new Tarefa(nome, descricao, nivelPrioridade,
                categoria, status, dataDeTermino, alarme));

        listaTarefa.sort(this::listaSort);
    }

    public List<Tarefa> listarTodasTarefas() {
        return this.listaTarefa;
    }


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

    public void editarTarefa(String nome, String novoNome,
                             String novaDescricao, Integer novaPrioridade,
                             String novaCategoria, Status novoStatus,
                             LocalDate novaData, Boolean novoAlarme) {

        Tarefa tarefa = listarTarefa(nome);
        if (tarefa == null) {
            throw new RuntimeException("Tarefa não encontrada - " + nome);
        }

        if (novoNome != null && !tarefa.getNome().equalsIgnoreCase(novoNome)) {
            boolean existe = listaTarefa.stream()
                    .anyMatch(t -> t.getNome().equalsIgnoreCase(novoNome));
            if (existe) {
                throw new RuntimeException("Já existe uma tarefa com o nome: " + novoNome);
            }
            tarefa.setNome(novoNome);
        }

        if (novaDescricao != null) tarefa.setDescricao(novaDescricao);
        if (novaPrioridade != null) tarefa.setNivelPrioridade(novaPrioridade);
        if (novaCategoria != null) tarefa.setCategoria(novaCategoria);
        if (novoStatus != null) tarefa.setStatus(novoStatus);
        if (novaData != null) tarefa.setDataDeTermino(novaData);
        if (novoAlarme != null) tarefa.setAlarme(novoAlarme);

        listaTarefa.sort(this::listaSort);
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
            return -1;
        } else if (t1.getNivelPrioridade() < t2.getNivelPrioridade()) {
            return 1;
        } else {
            return 0;
        }
    }
    public void verificarAlarmes() {
        LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        for (Tarefa tarefa : listaTarefa) {
            if (tarefa.isAlarme() && tarefa.getStatus() != Status.DONE) {
                LocalDate data = tarefa.getDataDeTermino();

                if (data.isEqual(hoje)) {
                    System.out.println("ALARME! A tarefa \"" + tarefa.getNome() + "\" é para hoje!");
                } else if (data.isEqual(hoje.plusDays(1))) {
                    System.out.println("Lembrete: A tarefa \"" + tarefa.getNome() + "\" é para amanhã!");
                } else if (data.isBefore(hoje)) {
                    System.out.println("A tarefa \"" + tarefa.getNome() + "\" está atrasada!");
                }
            }
        }
    }


}