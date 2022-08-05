package top.imwonder.mcauth.dao.user;

import org.springframework.stereotype.Component;

import top.imwonder.mcauth.domain.User;
import top.imwonder.util.AbstractDAO;

@Component
public class UserPwdDAO extends AbstractDAO<User> {

    @Override
    protected Class<User> getDomainType() {
        return User.class;
    }

    @Override
    protected String getTableName() {
        return "w_user";
    }

    @Override
    protected String[] getPkColumns() {
        return new String[] { "w_id" };
    }

    @Override
    protected String[] getCkColumns() {
        return new String[] { "w_username", "w_email", "w_password" };
    }
}
