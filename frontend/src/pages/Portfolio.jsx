import React, { useState, useEffect } from 'react';
import logotipo from '../images/logotipo.png';
import { jwtDecode } from 'jwt-decode';





export default function Portfolio() {

  
  const projetosMock = [
    {
      titulo: 'Loja Virtual',
      descricao: 'E-commerce completo com carrinho, pagamentos via PIX e painel de controle.',
      imagem: 'https://media.istockphoto.com/id/902893122/pt/vetorial/laptop-and-web-store-on-screen-vector-illustration-in-flat-design-style.jpg?s=612x612&w=0&k=20&c=jBpAnOVwORilERdhMQbvr4uQYj562XYq_5FuPKez_ME=',
      tecnologias: ['React', 'Django', 'Stripe']
    },
    {
      titulo: 'Dashboard Financeiro',
      descricao: 'Sistema com gráficos interativos e controle financeiro pessoal e empresarial.',
      imagem: 'https://dashboardacademy.com.br/wp-content/uploads/2023/03/Dashboard-de-Controle-Financeiro-Geral-1024x576.png',
      tecnologias: ['React', 'Chart.js', 'Node.js']
    },
    {
      titulo: 'Site Institucional para Clínica',
      descricao: 'Site moderno com formulário de contato, agendamento de consultas e SEO otimizado.',
      imagem: 'https://static.vecteezy.com/ti/vetor-gratis/p1/7696398-medico-e-paciente-na-mesa-no-hospital-escritorio-clinica-visita-para-exame-reuniao-com-medico-conversa-com-medico-sobre-diagnostico-resultados-cartoon-design-vetor.jpg',
      tecnologias: ['HTML', 'CSS', 'JavaScript', 'PHP']
    }
  ];

  const [username, setUsername] = useState('');
  const [busca, setBusca] = useState('');
  const [projetos, setProjetos] = useState(projetosMock);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded = jwtDecode(token)
      setUsername(decoded.sub); // ou decoded.username, dependendo do que você setou no token
    }

    const buscarProjetos = async () => {
      if (busca.trim() === '') {
        setProjetos(projetosMock);
        return;
      }

      try {
        // Substitua isso pela sua API real no futuro
        const response = await fetch(`https://sua-api.com/projetos?search=${busca}`);
        const data = await response.json();
        setProjetos(data);
      } catch (error) {
        console.error('Erro ao buscar projetos:', error);
        setProjetos([]); // ou setProjetos(projetosMock);
      }
    };

    buscarProjetos();
  }, [busca]);

  return (
    <div className="main-layout">
      <aside className="sidebar">
        <div className="logo">  <img src={logotipo} alt="Logo" /> </div>
        <div className="profile-section">
          <div className="profile-icon profile-center">👤</div>
          <div className="profile-name profile-center">{username}</div>
          <div className="profile-info">
            <ul>
              <li><a href="#">Minha conta</a></li>
              <li><a href="#">Meu portfólio</a></li>
              <li><a href="#">Meus projetos</a></li>
            </ul>
          </div>
        </div>

        <div className="menu-section">
          <p><strong>Negócios</strong></p>
          <ul>
            <li><a href="#">Consultar projetos abertos</a></li>
            <li><a href="#">Projetos mais recentes</a></li>
            <li><a href="#">Abrir um projeto</a></li>
            <li><a href="#">Chat com clientes</a></li>
          </ul>
        </div>
        
        <div className="filter-section">
          <p><strong>Filtrar por Linguagens</strong></p>
          <label><input type="checkbox" /> JavaScript</label>
          <label><input type="checkbox" /> Python</label>
          <label><input type="checkbox" /> Django </label>
          <label><input type="checkbox" /> Flask </label>
          <label><input type="checkbox" /> Java</label>
          <label><input type="checkbox" /> HTML - CSS</label>
          <label><input type="checkbox" /> Golang</label>
          <label><input type="checkbox" /> Angular</label>
          <label><input type="checkbox" /> React</label>
          <label><input type="checkbox" /> Vue.js</label>
          <label><input type="checkbox" /> Spring Boot framework</label>
          <label><input type="checkbox" /> C# </label>
          <label><input type="checkbox" /> ASP.NET </label>
        </div>
      </aside>

      <div className="portfolio-page">
        <h1>projetos em destaque</h1>

        {/* Campo de Busca */}
        <input
          type="text"
          placeholder="Buscar projetos..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          className="busca-input"
          style={{ display: 'block',margin: '0 auto', padding: '10px', width: '90%', maxWidth: '800px' }}
        />

        <div className="projects-flex">
          {projetos.length > 0 ? (
            projetos.map((projeto, index) => (
              <div key={index} className="project-card">
                <img src={projeto.imagem} alt={projeto.titulo} />
                <div className="project-content">
                  <h2>{projeto.titulo}</h2>
                  <p>{projeto.descricao}</p>
                  <div className="tech-list">
                    {projeto.tecnologias.map((tech, i) => (
                      <span key={i} className="tech-badge">{tech}</span>
                    ))}
                  </div>
                  <button className="view-button">Ver Projeto</button>
                </div>
              </div>
            ))
          ) : (
            <p>Nenhum projeto encontrado.</p>
          )}
        </div>
      </div>
    </div>
  );
}
