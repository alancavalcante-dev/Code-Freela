// utils.js
export const isLoggedIn = () => {
    const token = localStorage.getItem('token');  // Verifica se o token está no localStorage
    return token ? true : false;  // Se tiver o token, retorna true (logado), senão false (não logado)
  };
  