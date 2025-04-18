import { useState } from 'react';
import { login } from '../services/auth'; // sua função de login
import { Navigate } from 'react-router-dom'; // Importando o Navigate do React Router



function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [redirect, setRedirect] = useState(false); // Controle para redirecionar após login


  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await login(username, password);

    if (result.success) {
      console.log('Token:', result.token);
      localStorage.setItem('token', result.token); // Armazenando o token no localStorage
      setRedirect(true); // Ativando o redirecionamento
    } else {
      setError(result.message);
    }
  };

  const handleOAuthLogin = () => {
    // Implementação do login com OAuth
    console.log('Login com OAuth');
  };

  return (
    <div className="flex-authentication">
      <div className="container-authentication">
        <h2>Login</h2>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Usuário"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          /><br />

          <input
            type="password"
            placeholder="Senha"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          /><br />

          <button type="submit">Entrar</button>
        </form>

        <hr style={{ margin: '20px 0' }} />

        <button onClick={handleOAuthLogin}>Entrar com OAuth</button>
      </div>
    </div>
  );
}

export default LoginPage;
