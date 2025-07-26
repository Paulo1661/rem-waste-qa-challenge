import { Router } from 'express';
import {
    getUsers,
    createUser,
    updateUser,
    deleteUser
} from '../controllers/user.controller';
import {login} from "../controllers/auth.controller";

const router = Router();

router.post('/login', login);
router.get('/', getUsers);
router.post('/', createUser);
router.put('/:id', updateUser);     // Update
router.delete('/:id', deleteUser);  // Delete

export default router;
