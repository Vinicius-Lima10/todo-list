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

function listarTarefas(tarefas=null) {
    let indice = 0;
    if (!tarefas) {
        tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    }

    for (let i = 0; i < tarefas.length; i++) {
        let tarefa = tarefas[i];
        let tarefasHtml = `
                    <div class="tarefa row">
                        <h3 class="col-3">${tarefa.nome}</h3>
                        <p class="col-6">${tarefa.descricao}</p>
                        <div class="col-3">
                            <button id="botaoEditar" onclick="editar(${indice})" class="btn btn-primary col-5">Editar</button>
                            <button id="botaoDeletar" onclick="deletarTarefa(${indice})" class="btn btn-danger col-5">Remover</button>
                        </div>
                        <div class="row">
                            <div class="col-3">Nível de prioridade: ${tarefa.nivelPrioridade}</div>
                            <div class="col-3">Categoria: ${tarefa.categoria}</div>
                            <div class="col-3">Status: ${tarefa.status}</div>
                            <div class="col-3">Data de término: ${tarefa.dataDeTermino}</div>
                        </div>
                    </div>
                `;
        document.getElementById("tarefas").innerHTML += tarefasHtml;
        indice += 1;
    }
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
    console.log(status)
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