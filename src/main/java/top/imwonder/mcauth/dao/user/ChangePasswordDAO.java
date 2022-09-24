package top.imwonder.mcauth.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.imwonder.mcauth.pojo.requestbody.ForgetPasswordReq;

import java.util.ArrayList;

@Component
public class ChangePasswordDAO  {
    @Autowired
    private JdbcTemplate jt;

    String updatePasswordSql="update w_user set w_password=? where w_email=?";


    public int changePassword(ForgetPasswordReq req){
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(req.getPassword());
        objects.add(req.getEmail());
       return jt.update(updatePasswordSql, objects.toArray());
    }


}
