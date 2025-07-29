import { Request, Response } from 'express';
import { User } from '../models/user.model';

let users: User[] = [];

export const getUsers = (req: Request, res: Response) => {
    res.json(users);
};

export const createUser = (req: Request, res: Response) => {
    const { name, email } = req.body;
    const newUser: User = {
        id: Date.now(),
        name,
        email
    };
    users.push(newUser);
    res.status(201).json(newUser);
};

export const updateUser = (req: Request, res: Response) => {
    const { id } = req.params;
    const { name, email } = req.body;
    const userId = parseInt(id);

    const user = users.find(u => u.id === userId);
    if (!user) {
        return res.status(404).json({ message: 'User not found' });
    }

    user.name = name ?? user.name;
    user.email = email ?? user.email;

    res.json(user);
};

export const deleteUser = (req: Request, res: Response) => {
    const { id } = req.params;
    const userId = parseInt(id);

    const index = users.findIndex(u => u.id === userId);
    if (index === -1) {
        return res.status(404).json({ message: 'User not found' });
    }

    const deleted = users.splice(index, 1)[0];
    res.json(deleted);
};

export const deleteAllUser = (req: Request, res: Response) => {
    console.log("removing all users")
    users = [];
    res.status(200).json({ message: 'DB cleaned' });
};
