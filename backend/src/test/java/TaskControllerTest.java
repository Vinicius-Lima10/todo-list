import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class TaskControllerTest {

    @Test
    void deveCriarTarefaComSucesso() {
        //Given
        TaskController controller = new TaskController();

        //When
        controller.criarTarefa(
                "Estudar Java",
                "Revisar TDD",
                3,
                "Estudos",
                Status.TODO,
                LocalDate.of(2025, 1, 10),
                true
        );

        //Then
        Tarefa tarefa = controller.listarTarefa("Estudar Java");
        assertNotNull(tarefa);
        assertEquals("Revisar TDD", tarefa.getDescricao());
        assertEquals(3, tarefa.getNivelPrioridade());

        System.out.println("Criando >> Tarefa 'Estudar Java' >> criada com sucesso!");
    }

    @Test
    void deveEditarTarefa() {

        //Given
        TaskController controller = new TaskController();
        controller.criarTarefa("Gym", "Treinar peito", 3, "Saude", Status.TODO, LocalDate.now(), false);

        //When
        controller.editarTarefa(
                "Gym",
                "Academia",
                "Treinar costas",
                5,
                "Bem-estar",
                Status.DOING,
                LocalDate.now().plusDays(1),
                true
        );

        Tarefa t = controller.listarTarefa("Academia");

        //Then
        assertEquals("Treinar costas", t.getDescricao());
        assertEquals(5, t.getNivelPrioridade());
        assertEquals(Status.DOING, t.getStatus());
        assertTrue(t.isAlarme());
    }

    @Test
    void deveRemoverTarefa() {
        //Given
        TaskController controller = new TaskController();

        //WHen
        controller.criarTarefa("Pagar contas", "Água e luz", 2, "Financeiro", Status.TODO, LocalDate.now(), false);

        controller.deletarTarefa("Pagar contas");

        //Then
        assertNull(controller.listarTarefa("Pagar contas"));
    }

}
