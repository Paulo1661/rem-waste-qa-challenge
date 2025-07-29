type User = {
    id: number;
    name: string;
    email: string;
};

type Props = {
    users: User[];
    onEdit: (user: User) => void;
    onDelete: (id: number) => void;
};

export default function UserList({ users, onEdit, onDelete }: Props) {
    if (users.length === 0) {
        return null;
    }

    return (
        <div>
            <h2>Users List</h2>
            <ul>
                {users.map(u => (
                    <li key={u.id}>
                        <span>{u.name} ({u.email})</span>{' '}
                        <button id="edit-item" onClick={() => onEdit(u)}>✏️</button>
                        <button id="delete-item" onClick={() => onDelete(u.id)}>🗑️</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

