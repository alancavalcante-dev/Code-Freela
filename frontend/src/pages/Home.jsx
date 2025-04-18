// Home.jsx
import React from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div className="container">
      <h1>Bem-vindo ao Sistema</h1>
      <p>Faça login ou registre-se para começar a usar o sistema.</p>
      <div className="links">
        <Link to='/login' className="link">Login</Link> | 
        <Link to='/register' className="link">Registrar</Link>
      </div>
    </div>
  );
}
