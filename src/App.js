import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [alimentos, setAlimentos] = useState([]);
    const [receita, setReceita] = useState("");
    const [novoNome, setNovoNome] = useState("");

    // Buscar alimentos na inicialização
    useEffect(() => {
        carregarAlimentos();
    }, []);

    const carregarAlimentos = () => {
        axios.get('http://localhost:8080/food')
            .then(res => setAlimentos(res.data))
            .catch(err => console.error("Erro ao buscar alimentos:", err));
    };

    const pedirReceita = () => {
        axios.get('http://localhost:8080/ia/sugerir')
            .then(res => setReceita(res.data))
            .catch(err => console.error("Erro ao pedir receita:", err));
    };

    const adicionarAlimento = () => {
        if (novoNome.trim() === "") return;

        const alimento = {
            name: novoNome,
            categoria: "Geral",
            quantidade: 1,
            validade: "2025-12-31T23:59:00" // você pode melhorar isso futuramente
        };

        axios.post('http://localhost:8080/food', alimento)
            .then(() => {
                setNovoNome("");
                carregarAlimentos();
            })
            .catch(err => console.error("Erro ao adicionar alimento:", err));
    };

    const deletarAlimento = (id) => {
        axios.delete(`http://localhost:8080/food/${id}`)
            .then(() => carregarAlimentos())
            .catch(err => console.error("Erro ao deletar alimento:", err));
    };

    return (
        <div style={{ padding: '2rem', fontFamily: 'Arial' }}>
            <h1>🍽️ Geladeira Inteligente</h1>

            <h2>📦 Alimentos:</h2>
            <ul>
                {alimentos.map((item) => (
                    <li key={item.id}>
                        {item.name} ({item.quantidade}) -
                        <button onClick={() => deletarAlimento(item.id)} style={{ marginLeft: '10px' }}>
                            ❌ Excluir
                        </button>
                    </li>
                ))}
            </ul>

            <h3>➕ Adicionar Alimento</h3>
            <input
                value={novoNome}
                onChange={(e) => setNovoNome(e.target.value)}
                placeholder="Ex: Tomate"
            />
            <button onClick={adicionarAlimento}>Adicionar</button>

            <hr />

            <button onClick={pedirReceita}>🔮 Sugerir Receita com IA</button>

            {receita && (
                <div style={{ marginTop: '2rem' }}>
                    <h2>🍝 Receita Sugerida:</h2>
                    <p>{receita}</p>
                </div>
            )}
        </div>
    );
}

export default App;
