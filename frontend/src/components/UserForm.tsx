import { useState, useEffect } from 'react';

type User = {
    id?: number;
    name: string;
    email: string;
};

type Props = {
    onSubmit: (user: User) => void;
    editingUser?: User | null;
};

export default function UserForm({ onSubmit, editingUser }: Props) {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    useEffect(() => {
        if (editingUser) {
            setName(editingUser.name);
            setEmail(editingUser.email);
        }
    }, [editingUser]);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit({ id: editingUser?.id, name, email });
        setName('');
        setEmail('');
    };

    const formRowStyle: React.CSSProperties = {
        display: 'flex',
        gap: '0.5rem',
        alignItems: 'center',
    };

    const inputStyle: React.CSSProperties = {
        padding: '0.5rem',
        border: '1px solid #ccc',
        borderRadius: '6px',
        fontSize: '1rem',
        flex: 1,
    };

    const buttonStyle: React.CSSProperties = {
        padding: '0.5rem 1rem',
        backgroundColor: '#3b82f6',
        color: 'white',
        border: 'none',
        borderRadius: '6px',
        fontSize: '1rem',
        cursor: 'pointer',
        whiteSpace: 'nowrap',
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>{editingUser ? 'Edit user' : 'Add user'}</h2>
            <div style={formRowStyle}>
                <input
                    id="name"
                    style={inputStyle}
                    value={name}
                    onChange={e => setName(e.target.value)}
                    placeholder="Name"
                    required
                />
                <input
                    id="email"
                    style={inputStyle}
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    placeholder="Email"
                    required
                />
                <button id="submit-user-btn" type="submit" style={buttonStyle}>
                    {editingUser ? 'Update' : 'Create'}
                </button>
            </div>
        </form>
    );
}
