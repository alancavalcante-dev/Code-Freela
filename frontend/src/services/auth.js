import axios from 'axios';

export async function login(username, password) {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      username,
      password,
    });

    const token = response.data.token;
    // Armazena o token no localStorage (ou cookies, se preferir)
    localStorage.setItem('token', token);

    return { success: true, token };
  } catch (error) {
    console.error('Erro no login:', error);
    return {
      success: false,
      message: 'Usuário ou senha inválidos.',
    };
  }
}
