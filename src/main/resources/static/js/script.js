const tokenKey = 'token';

// Define a URL base automaticamente: se estiver no localhost usa a porta 8080, se estiver em produção (Render) usa vazio (mesmo domínio)
const API_URL = window.location.hostname === 'localhost' ? 'http://localhost:8080' : '';

// Ao carregar a página, verifica se já está logado
window.onload = function() {
    const token = localStorage.getItem(tokenKey);
    if (token) {
        mostrarApp();
        carregarTarefas();
    }
};

// 1. LOGIN (POST /login)
document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const token = response.headers.get('Authorization');
            if (token) {
                localStorage.setItem(tokenKey, token);
                mostrarApp();
                carregarTarefas();
            } else {
                alert('Token não encontrado na resposta do servidor.');
            }
        } else {
            alert('Usuário ou senha inválidos!');
        }
    } catch (error) {
        console.error('Erro no login:', error);
        alert('Erro ao conectar com o servidor.');
    }
});

function mostrarApp() {
    document.getElementById('loginSection').classList.add('hidden');
    document.getElementById('navbar').classList.remove('hidden'); // Exibe a Navbar
    document.getElementById('appSection').classList.remove('hidden');
}

function logout() {
    localStorage.removeItem(tokenKey);
    document.getElementById('navbar').classList.add('hidden'); // Esconde a Navbar
    document.getElementById('appSection').classList.add('hidden');
    document.getElementById('loginSection').classList.remove('hidden');
}

// 2. LISTAR TAREFAS DO USUÁRIO LOGADO (GET /task/user)
async function carregarTarefas() {
    const token = localStorage.getItem(tokenKey);
    try {
        const response = await fetch(`${API_URL}/task/user`, {
            method: 'GET',
            headers: { 'Authorization': token }
        });

        if (response.ok) {
            const tasks = await response.json();
            const taskList = document.getElementById('taskList');
            taskList.innerHTML = '';

            if (tasks.length === 0) {
                taskList.innerHTML = '<p style="color: #6c757d; font-size: 14px; text-align: center; padding: 15px;">Nenhuma tarefa cadastrada no momento.</p>';
                return;
            }

            tasks.forEach(task => {
                const div = document.createElement('div');
                div.className = 'task-item';
                div.innerHTML = `
                    <div class="task-content">
                        <strong>ID: #${task.id}</strong>
                        <span>${task.description}</span>
                    </div>
                    <div class="actions">
                        <button onclick="deletarTarefa(${task.id})" class="btn-danger">Excluir</button>
                    </div>
                `;
                taskList.appendChild(div);
            });
        } else if (response.status === 403 || response.status === 401) {
            alert('Sessão expirada. Faça login novamente.');
            logout();
        }
    } catch (error) {
        console.error('Erro ao carregar tarefas:', error);
    }
}

// 3. CRIAR TAREFA (POST /task/0)
document.getElementById('taskForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const description = document.getElementById('taskDescription').value;
    const token = localStorage.getItem(tokenKey);

    try {
        const response = await fetch(`${API_URL}/task/0`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify({ description })
        });

        if (response.ok || response.status === 201) {
            document.getElementById('taskDescription').value = '';
            carregarTarefas();
        } else {
            alert('Erro ao criar tarefa.');
        }
    } catch (error) {
        console.error('Erro:', error);
    }
});

// 4. DELETAR TAREFA (DELETE /task/{id})
async function deletarTarefa(id) {
    if (!confirm('Deseja realmente excluir esta tarefa?')) return;
    const token = localStorage.getItem(tokenKey);

    try {
        const response = await fetch(`${API_URL}/task/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': token }
        });

        if (response.ok || response.status === 204) {
            carregarTarefas();
        } else {
            alert('Erro ao excluir tarefa.');
        }
    } catch (error) {
        console.error('Erro ao deletar:', error);
    }
}