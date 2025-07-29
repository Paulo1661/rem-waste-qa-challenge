import { useEffect, useState } from 'react';
import axios from '../utils/axios';
import { useAuth } from '../context/AuthContext';
import UserForm from '../components/UserForm';
import UserList from '../components/UserList';
import '../Users.css';
import {toast} from "react-toastify";

type User = {
    id: number;
    name: string;
    email: string;
};

export default function UsersPage() {
    const { user, logout } = useAuth();
    const [users, setUsers] = useState<User[]>([]);
    const [editingUser, setEditingUser] = useState<User | null>(null);

    const fetchUsers = async () => {
        const res = await axios.get('/users');
        setUsers(res.data);
    };

    const createOrUpdateUser = async (user: Partial<User>) => {
        try {
            if (user.id) {
                const res = await axios.put(`/users/${user.id}`, user);
                setUsers(prev =>
                    prev.map(u => (u.id === user.id ? res.data : u))
                );
                toast.success('User updated successfully');
            } else {
                const res = await axios.post('/users', user);
                setUsers(prev => [...prev, res.data]);
                toast.success('User added successfully');
            }
            setEditingUser(null);
        } catch (err) {
            console.error(err);
            toast.error('Something went wrong while saving the user');
        }
    };

    const deleteUser = async (id: number) => {
        await axios.delete(`/users/${id}`);
        setUsers(prev => prev.filter(u => u.id !== id));
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    return (
        <div className="users-container">
            <div className="users-header">
                <h1>Welcome <strong>{user?.name}</strong></h1>
                <button className="logout-button" onClick={logout}>
                    Logout
                </button>
            </div>

            <div className="user-form-container">
                <UserForm
                    onSubmit={createOrUpdateUser}
                    editingUser={editingUser}
                />
            </div>

            <div className="user-list-container">
                <UserList
                    users={users}
                    onEdit={setEditingUser}
                    onDelete={deleteUser}
                />
            </div>
        </div>
    );
}
