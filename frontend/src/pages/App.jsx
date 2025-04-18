import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { isLoggedIn } from './utils';  // Certifique-se de que o arquivo utils.js existe
import Login from './Login';
import Register from './Register';
import ProtectedPage from './ProtectedPage';
import Home from './Home';

export default function App() {
  return (
    <Routes>
      {/* Páginas acessíveis sem login */}
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      {/* Páginas protegidas, exige login */}
      <Route
        path="/protected"
        element={isLoggedIn() ? <ProtectedPage /> : <Navigate to="/login" />}
      />

      {/* Redireciona para login caso a rota não seja encontrada */}
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  );
}
