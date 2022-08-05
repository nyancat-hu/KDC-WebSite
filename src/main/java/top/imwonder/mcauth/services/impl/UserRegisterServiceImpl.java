package top.imwonder.mcauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imwonder.mcauth.dao.user.UserDAO;
import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.services.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public int insertUser(User user) {
        return userDAO.insert(user);
    }
}
