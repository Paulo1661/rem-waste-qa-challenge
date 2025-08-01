import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.tsx';
import {JSX} from "react";

export const PrivateRoute = ({ children }: { children: JSX.Element }) => {
    const { token } = useAuth();
    return token ? children : <Navigate to="/login" />;
};