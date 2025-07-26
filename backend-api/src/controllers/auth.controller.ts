import { Request, Response } from 'express';
import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';
import dotenv from 'dotenv';

dotenv.config();

const JWT_SECRET = process.env.JWT_SECRET || "F$5mT9@xLq%cV2*eRz7!HnJwK0Pb1Dg8"; // ⚠️ put in process.env in production

const fakeUser = {
    id: 1,
    name: 'Paul',
    email: 'paul@example.com',
    password: bcrypt.hashSync('password123', 8), // hash
};

export const login = (req: Request, res: Response) => {
    const { email, password } = req.body;

    if (email !== fakeUser.email || !bcrypt.compareSync(password, fakeUser.password)) {
        return res.status(401).json({ message: 'Invalid credentials' });
    }

    const token = jwt.sign({ id: fakeUser.id, email: fakeUser.email }, JWT_SECRET, {
        expiresIn: '1h',
    });

    res.json({ token, user: { id: fakeUser.id, name: fakeUser.name, email: fakeUser.email } });
};
