import { Router } from 'express';
import {
    getUsers,
    createUser,
    updateUser,
    deleteUser,
    deleteAllUser
} from '../controllers/user.controller';
import {authenticateJWT} from "../middlewares/auth.middleware";

const router = Router();

router.use(authenticateJWT);
router.get('/', getUsers);
router.post('/', createUser);
router.put('/:id', updateUser);
router.delete('/:id', deleteUser);
router.delete('/', deleteAllUser);

export default router;
