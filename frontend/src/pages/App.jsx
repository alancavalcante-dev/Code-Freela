import React from 'react';
import { Link } from 'react-router-dom';

export default function App() {
  return (
    <div className="container">
      <h1>Bem-vindo ao sistema</h1>
      <Link to='/login'>Login</Link> | <Link to='/register'>Registrar</Link>
    </div>

  );
}