package top.imwonder.mcauth.dao;

import org.springframework.stereotype.Component;

import top.imwonder.mcauth.domain.Texture;
import top.imwonder.util.AbstractDAO;

@Component
public class TextureDAO extends AbstractDAO<Texture> {

    @Override
    protected Class<Texture> getDomainType() {
        return Texture.class;
    }

    @Override
    protected String getTableName() {
        return "w_texture";
    }

    @Override
    protected String[] getPkColumns() {
        return new String[] { "w_id" };
    }

    @Override
    protected String[] getCkColumns() {
        return new String[] { "w_hash", "w_name", "w_type", "w_size", "w_upid", "w_time" };
    }

}
