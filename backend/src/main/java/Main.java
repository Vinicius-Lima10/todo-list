import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        TaskController taskController = new TaskController();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            taskController.verificarAlarmes();
            System.out.println("\n--- TODO LIST ---");
            System.out.println("1 - Adicionar Tarefa");
            System.out.println("2 - Listar Tarefas");
            System.out.println("3 - Remover Tarefa");
            System.out.println("4 - Filtrar por Categoria");
            System.out.println("5 - Filtrar por Status");
            System.out.println("6 - Filtrar por Prioridade");
            System.out.println("7 - Editar Tarefa");
            System.out.println("8 - Buscar por Tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.println("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.println("Descrição: ");
                    String desc = scanner.nextLine();
                    System.out.println("Prioridade (1-5): ");
                    int prioridade = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.println("Status (TODO, DOING, DONE): ");
                    Status status = Status.valueOf(scanner.nextLine().toUpperCase());
                    System.out.println("Data de término (YYYY-MM-DD): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine());
                    System.out.println("Ativar alarme? (s/n): ");
                    boolean alarme = scanner.nextLine().equalsIgnoreCase("s");

                    taskController.criarTarefa(nome, desc, prioridade, categoria, status, data, alarme);
                }
                case 2 -> {
                    if (taskController.listarTodasTarefas().isEmpty()) {
                        System.out.println("Nenhuma tarefa cadastrada.");
                    } else {
                        taskController.listarTodasTarefas().forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Nome da tarefa a remover: ");
                    String nomeRemover = scanner.nextLine();
                    taskController.deletarTarefa(nomeRemover);
                }
                case 4 -> {
                    System.out.print("Categoria para filtrar: ");
                    String categoria = scanner.nextLine();
                    taskController.filtrarPorCategoria(categoria)
                            .forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Status (TODO, DOING, DONE): ");
                    Status status = Status.valueOf(scanner.nextLine().toUpperCase());
                    taskController.filtrarPorStatus(status)
                            .forEach(System.out::println);
                }
                case 6 -> {
                    System.out.print("Prioridade (1-5): ");
                    int prioridade = scanner.nextInt();
                    scanner.nextLine();
                    taskController.filtrarPorPrioridade(prioridade)
                            .forEach(System.out::println);
                }
                case 7 -> {
                    System.out.println("Nome da tarefa a editar: ");
                    String nomeAntigo = scanner.nextLine();

                    System.out.println("Novo nome (ou deixe vazio p/ manter): ");
                    String novoNome = scanner.nextLine();
                    if (novoNome.isBlank()) novoNome = null;

                    System.out.println("Nova descrição (ou deixe vazio): ");
                    String novaDesc = scanner.nextLine();
                    if (novaDesc.isBlank()) novaDesc = null;

                    System.out.println("Nova prioridade (1-5 ou 0 p/ manter): ");
                    int novaPrioridade = scanner.nextInt();
                    scanner.nextLine();
                    Integer prioridadeObj = (novaPrioridade == 0) ? null : novaPrioridade;

                    System.out.println("Nova categoria (ou deixe vazio): ");
                    String novaCategoria = scanner.nextLine();
                    if (novaCategoria.isBlank()) novaCategoria = null;

                    System.out.println("Novo status (TODO, DOING, DONE ou vazio): ");
                    String statusStr = scanner.nextLine();
                    Status novoStatus = statusStr.isBlank() ? null : Status.valueOf(statusStr.toUpperCase());

                    System.out.println("Nova data término (YYYY-MM-DD ou vazio): ");
                    String dataStr = scanner.nextLine();
                    LocalDate novaData = dataStr.isBlank() ? null : LocalDate.parse(dataStr);

                    System.out.println("Alterar alarme? (s/n ou vazio p/ manter): ");
                    String alarmeStr = scanner.nextLine();
                    Boolean novoAlarme = null;
                    if (alarmeStr.equalsIgnoreCase("s")) novoAlarme = true;
                    else if (alarmeStr.equalsIgnoreCase("n")) novoAlarme = false;

                    taskController.editarTarefa(nomeAntigo, novoNome, novaDesc, prioridadeObj, novaCategoria,
                            novoStatus, novaData, novoAlarme );
                }
                case 8 -> {
                    System.out.println("Digite o nome da tarefa");
                    String nome = scanner.nextLine();
                    System.out.println(taskController.listarTarefa(nome));
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                }

                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
