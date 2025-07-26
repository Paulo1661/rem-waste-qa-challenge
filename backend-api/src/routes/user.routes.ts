import { Router } from 'express';
import {
    getUsers,
    createUser,
    updateUser,
    deleteUser
} from '../controllers/user.controller';

const router = Router();

router.get('/', getUsers);
router.post('/', createUser);
router.put('/:id', updateUser);     // Update
router.delete('/:id', deleteUser);  // Delete

export default router;
