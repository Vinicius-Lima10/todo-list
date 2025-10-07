function criarTarefa()
{
    document.getElementById("criarTarefa").addEventListener("submit", function (event) {
        event.preventDefault();

        const tarefa = {
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
    indice = localStorage.getItem("tarefaEditando")
    if (indice !== null) {
        let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
        if (!tarefas) {
            location.href = "index.html"
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
            tarefa.nome = document.getElementById("nome").value;
            tarefa.status = document.getElementById("status").value;
            tarefa.dataDeTermino = document.getElementById("dataDeTermino").value;
            tarefa.nivelPrioridade = document.getElementById("nivelPrioridade").value;
            tarefa.categoria = document.getElementById("categoria").value;
            tarefa.descricao = document.getElementById("descricao").value;

            tarefas[indice] = tarefa
            localStorage.setItem("tarefas", JSON.stringify(tarefas));

            document.getElementById("mensagemSucesso").style.display = "block";
            setTimeout(() => {

                document.getElementById("mensagemSucesso").style.display = "none";
                localStorage.removeItem("tarefaEditando")
                location.href = "index.html"
            }, 5000);
        });
    }

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
    localStorage.setItem("tarefaEditando", indice);
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
