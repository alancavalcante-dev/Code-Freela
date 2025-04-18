// src/pages/OAuthCallback.jsx
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function OAuthCallback() {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const redirectUri = 'http://localhost:5173/authorized';
    const clientId = 'meu-client';
    const clientSecret = 'secret-client'; // cuidado com isso, use PKCE em produção!

    const getToken = async () => {
      try {
        const basicAuth = btoa(`${clientId}:${clientSecret}`);

        const response = await axios.post('http://localhost:8080/oauth2/token', {
          grant_type: 'authorization_code',
          code,
          redirect_uri: redirectUri,
        }, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': `Basic ${basicAuth}`,
          },
          transformRequest: [(data) => {
            const params = new URLSearchParams();
            for (let key in data) {
              params.append(key, data[key]);
            }
            return params.toString();
          }]
        });

        localStorage.setItem('token', response.data.access_token);
        alert('Login via OAuth realizado!');
        navigate('/');
      } catch (err) {
        console.error(err);
        alert('Erro ao trocar o código por token');
      }
    };

    if (code) {
      getToken();
    } else {
      alert('Código de autorização não encontrado.');
    }
  }, [navigate]);

  return <p>Autenticando...</p>;
}
