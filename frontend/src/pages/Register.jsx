import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function Register() {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      await axios.post('http://localhost:8000/api/register/', {
        email,
        username,
        password
      });
      alert('Registrado com sucesso!');
      navigate('/login');
    } catch (err) {
      alert('Erro ao registrar');
    }
  };

  return (
    <div className="flex-authentication">
      <div className="container-authentication">
        <h2>Registrar</h2>
        <input type='text' placeholder='Username' onChange={e => setUsername(e.target.value)} />
        <input type='email' placeholder='Email' onChange={e => setEmail(e.target.value)} />
        <input type='password' placeholder='Senha' onChange={e => setPassword(e.target.value)} />
        <button onClick={handleRegister}>Registrar</button>
      </div>
    </div>

  );
}