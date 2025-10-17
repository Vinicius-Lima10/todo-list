import java.time.LocalDate;

public class Tarefa {
    private String nome;
    private String descricao;
    private int nivelPrioridade;
    private String categoria;
    private Status status;
    private LocalDate dataDeTermino;
    private boolean alarme;
    public Tarefa(String nome, String descricao,
                  int nivelPrioridade, String categoria,
                  Status status, LocalDate dataDeTermino,
                  boolean alarme) {
        this.nome = nome;
        this.descricao = descricao;
        setNivelPrioridade(nivelPrioridade);
        this.categoria = categoria;
        this.status = status;
        this.dataDeTermino = dataDeTermino;
        this.alarme = alarme;
    }
    public boolean isAlarme() {
        return alarme;
    }

    public void setAlarme(boolean alarme) {
        this.alarme = alarme;
    }
    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nivelPrioridade=" + nivelPrioridade +
                ", categoria='" + categoria + '\'' +
                ", status=" + status +
                ", dataDeTermino=" + dataDeTermino +
                '}';
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(int nivelPrioridade) {
        if (nivelPrioridade < 1 || nivelPrioridade > 5) {
            throw new IllegalArgumentException("Prioridade deve ser entre 1 e 5.");
        }
        this.nivelPrioridade = nivelPrioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }
}

