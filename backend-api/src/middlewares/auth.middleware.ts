import { Request, Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';

dotenv.config();

const JWT_SECRET = process.env.JWT_SECRET || "F$5mT9@xLq%cV2*eRz7!HnJwK0Pb1Dg8"; // ⚠️ put in process.env in production


export const authenticateJWT = (req: Request, res: Response, next: NextFunction) => {
    const authHeader = req.headers.authorization;

    if (!authHeader?.startsWith('Bearer ')) {
        return res.status(401).json({ message: 'No token provided' });
    }

    const token = authHeader.split(' ')[1];

    try {
        (req as any).user = jwt.verify(token, JWT_SECRET);
        next();
    } catch {
        res.status(403).json({ message: 'Invalid token' });
    }
};
