import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import UsersPage from './pages/UsersPage.tsx';
import { PrivateRoute } from './utils/PrivateRoute';
import './index.css';
import {ToastContainer} from "react-toastify";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/users" element={
                        <PrivateRoute>
                            <UsersPage />
                        </PrivateRoute>
                    } />
                    <Route path="/" element={<Navigate to="/users" replace />}/>
                </Routes>
            </BrowserRouter>
            <ToastContainer position="top-right" autoClose={3000} />
        </AuthProvider>

    );
}

export default App;
