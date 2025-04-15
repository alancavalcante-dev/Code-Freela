import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await axios.post('http://localhost:8000/api/token/', {
        email,
        password
      });
      localStorage.setItem('token', res.data.access);
      alert('Login realizado com sucesso!');
      navigate('/');
    } catch (err) {
      alert('Erro ao fazer login');
    }
  };

  return (
    <div className="flex-authentication">
      <div className="container-authentication">
        <h2>Login</h2>
        <input type='email' placeholder='Email' onChange={e => setEmail(e.target.value)} />
        <input type='password' placeholder='Senha' onChange={e => setPassword(e.target.value)} />
        <button onClick={handleLogin}>Entrar</button>
      </div>
    </div>
  

  );
}