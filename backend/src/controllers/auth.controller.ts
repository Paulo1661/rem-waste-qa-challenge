import { Request, Response } from 'express';
import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';
import dotenv from 'dotenv';

dotenv.config();

const JWT_SECRET = process.env.JWT_SECRET || "F$5mT9@xLq%cV2*eRz7!HnJwK0Pb1Dg8"; // ⚠️ Stocker dans .env en prod

const fakeUser = {
    id: 1,
    name: 'Paul',
    email: 'paul@example.com',
    password: bcrypt.hashSync('1234', 8), // hash
};

const apiUser = {
    id: 2,
    name: 'API-bot',
    email: 'api@example.com',
    password: bcrypt.hashSync('4321', 8), // hash
};


export const login = (req: Request, res: Response) => {
    console.log("🔐 Try to login");

    const { email, password } = req.body;

    const user = [fakeUser, apiUser].find(u => u.email === email);

    if (!user || !bcrypt.compareSync(password, user.password)) {
        return res.status(401).json({ message: 'Invalid credentials' });
    }

    const token = jwt.sign({ id: user.id, email: user.email }, JWT_SECRET, {
        expiresIn: '1h',
    });

    res.json({
        token,
        user: {
            id: user.id,
            name: user.name,
            email: user.email
        }
    });
};
