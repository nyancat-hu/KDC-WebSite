package top.imwonder.mcauth.dao;

import org.springframework.stereotype.Component;

import top.imwonder.mcauth.domain.Profile;
import top.imwonder.util.AbstractDAO;

@Component
public class ProfileDAO extends AbstractDAO<Profile> {

    @Override
    protected Class<Profile> getDomainType() {
        return Profile.class;
    }

    @Override
    protected String getTableName() {
        return "w_profile";
    }

    @Override
    protected String[] getPkColumns() {
        return new String[] { "w_id" };
    }

    @Override
    protected String[] getCkColumns() {
        return new String[] { "w_name", "w_uid", "w_skin_id", "w_cape_id" };
    }

}
