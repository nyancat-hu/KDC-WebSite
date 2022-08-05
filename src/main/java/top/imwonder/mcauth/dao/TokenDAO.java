package top.imwonder.mcauth.dao;

import org.springframework.stereotype.Component;

import top.imwonder.mcauth.domain.Token;
import top.imwonder.util.AbstractDAO;

@Component
public class TokenDAO extends AbstractDAO<Token> {

    @Override
    protected Class<Token> getDomainType() {
        return Token.class;
    }

    @Override
    protected String getTableName() {
        return "w_token";
    }

    @Override
    protected String[] getPkColumns() {
        return new String[] { "w_client_token", "w_access_token" };
    }

    @Override
    protected String[] getCkColumns() {
        return new String[] { "w_uid", "w_pid", "w_create_time", "w_access_time" };
    }

}
