import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.tsx';
import axios from '../utils/axios.ts';
import '../LoginPage.css';

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(''); // Pour gérer l’erreur
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(''); // Reset l’erreur à chaque tentative
        try {
            const res = await axios.post('/auth/login', { email, password });
            login(res.data.token, res.data.user);
            navigate('/users');
        } catch (err: any) {
            console.error(err);
            const errorMsg = err?.response?.data?.message || 'Login failed. Please try again.';
            setError(errorMsg);
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2 className="login-title">Login</h2>
                <form onSubmit={handleSubmit} className="login-form">
                    {error && <div className="login-error">{error}</div>} {/* Affichage de l’erreur */}

                    <label>
                        Email
                        <input
                            id="username"
                            type="email"
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            placeholder="you@example.com"
                            required
                        />
                    </label>

                    <label>
                        Password
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            placeholder="••••••••"
                            required
                        />
                    </label>

                    <button id="login-btn" type="submit">Login</button>
                </form>
            </div>
        </div>
    );
}
