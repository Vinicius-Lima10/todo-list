function criarTarefa()
{
    document.getElementById("criarTarefa").addEventListener("submit", function (event) {
        event.preventDefault();

        const tarefa = {
            id: crypto.randomUUID(),
            nome: document.getElementById("nome").value,
            status: document.getElementById("status").value,
            dataDeTermino: document.getElementById("dataDeTermino").value,
            nivelPrioridade: document.getElementById("nivelPrioridade").value,
            categoria: document.getElementById("categoria").value,
            descricao: document.getElementById("descricao").value
        };

        let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];

        tarefas.push(tarefa);

        localStorage.setItem("tarefas", JSON.stringify(tarefas));

        document.getElementById("criarTarefa").reset();

        document.getElementById("mensagemSucesso").style.display = "block";
        setTimeout(() => {
            document.getElementById("mensagemSucesso").style.display = "none";
        }, 5000);
    });
}

function editarTarefa() {
    const idTarefa = localStorage.getItem("tarefaEditando");
    if (!idTarefa) return;

    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    const indice = tarefas.findIndex(t => t.id === idTarefa);

    if (indice === -1) {
        alert("Essa tarefa foi removida ou alterada em outra aba.");
        localStorage.removeItem("tarefaEditando");
        location.href = "index.html";
        return;
    }

    const tarefa = tarefas[indice];

    document.getElementById("nome").value = tarefa.nome;
    document.getElementById("status").value = tarefa.status;
    document.getElementById("dataDeTermino").value = tarefa.dataDeTermino;
    document.getElementById("nivelPrioridade").value = tarefa.nivelPrioridade;
    document.getElementById("categoria").value = tarefa.categoria;
    document.getElementById("descricao").value = tarefa.descricao;

    document.getElementById("editarTarefa").addEventListener("submit", function (event) {
        event.preventDefault();

        let tarefasAtualizadas = JSON.parse(localStorage.getItem("tarefas")) || [];
        const i = tarefasAtualizadas.findIndex(t => t.id === idTarefa);

        if (i === -1) {
            alert("Essa tarefa foi removida ou alterada em outra aba.");
            localStorage.removeItem("tarefaEditando");
            location.href = "index.html";
            return;
        }

        tarefasAtualizadas[i] = {
            ...tarefasAtualizadas[i],
            nome: document.getElementById("nome").value,
            status: document.getElementById("status").value,
            dataDeTermino: document.getElementById("dataDeTermino").value,
            nivelPrioridade: document.getElementById("nivelPrioridade").value,
            categoria: document.getElementById("categoria").value,
            descricao: document.getElementById("descricao").value
        };

        localStorage.setItem("tarefas", JSON.stringify(tarefasAtualizadas));

        document.getElementById("mensagemSucesso").style.display = "block";
        setTimeout(() => {
            document.getElementById("mensagemSucesso").style.display = "none";
            localStorage.removeItem("tarefaEditando");
            location.href = "index.html";
        }, 3000);
    });
}

function listarTarefas(tarefas = null) {
    document.getElementById("tarefas").innerHTML = "";
    let todas = tarefas || JSON.parse(localStorage.getItem("tarefas")) || [];

    todas.forEach((tarefa, i) => {
        let tarefasHtml = `
            <div class="tarefa row border p-2 mb-2 align-items-center">
                <div class="col-1 text-center">
                    <input type="checkbox" class="selecionar-tarefa" value="${i}">
                </div>
                <div class="col-3"><strong>${tarefa.nome}</strong></div>
                <div class="col-4">${tarefa.descricao}</div>
                <div class="col-4 text-end">
                    <button onclick="editar(${i})" class="btn btn-primary btn-sm">Editar</button>
                    <button onclick="deletarTarefa(${i})" class="btn btn-danger btn-sm">Remover</button>
                </div>
                <div class="row mt-2">
                    <div class="col-3">Prioridade: ${tarefa.nivelPrioridade}</div>
                    <div class="col-3">Categoria: ${tarefa.categoria}</div>
                    <div class="col-3">Status: ${tarefa.status}</div>
                    <div class="col-3">Término: ${tarefa.dataDeTermino}</div>
                </div>
            </div>
        `;
        document.getElementById("tarefas").innerHTML += tarefasHtml;
    });
}


function editar(indice) {
    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    const tarefa = tarefas[indice];
    localStorage.setItem("tarefaEditando", tarefa.id);
    location.href = "editarTarefa.html";
}


function deletarTarefa(indice) {

    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    tarefas.splice(indice, 1);
    localStorage.setItem("tarefas", JSON.stringify(tarefas));

    document.getElementById("tarefas").innerHTML = ""
    listarTarefas()

}

function filtrarStatus(status) {
    document.getElementById("tarefas").innerHTML = ""
    tarefas = JSON.parse(localStorage.getItem("tarefas") || [])
    if(status === "0") {
        listarTarefas()
        return
    }
    tarefasFiltradas = []
    for(let i = 0; i < tarefas.length; i++) {
        if (tarefas[i].status == status) {
            tarefasFiltradas.push(tarefas[i])
        }
    }
    listarTarefas(tarefasFiltradas)
}

function alterarStatusSelecionados() {
    const novoStatus = document.getElementById("novoStatus").value;
    if (!novoStatus) {
        alert("Por favor, selecione um status.");
        return;
    }

    const checkboxes = document.querySelectorAll(".selecionar-tarefa:checked");
    if (checkboxes.length === 0) {
        alert("Nenhuma tarefa selecionada.");
        return;
    }

    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];

    checkboxes.forEach(cb => {
        const indice = parseInt(cb.value);
        tarefas[indice].status = novoStatus;
    });

    localStorage.setItem("tarefas", JSON.stringify(tarefas));
    listarTarefas(); // atualiza a tela
    document.getElementById("novoStatus").value = "";
}
