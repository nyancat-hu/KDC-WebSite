package top.imwonder.mcauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imwonder.mcauth.dao.user.ChangePasswordDAO;
import top.imwonder.mcauth.pojo.requestbody.ForgetPasswordReq;
import top.imwonder.mcauth.services.ChangeInfo;

@Service
public class ChangePasswordImpl implements ChangeInfo {
    @Autowired
    ChangePasswordDAO changePasswordDAO;

    @Override
    public int changePassword(ForgetPasswordReq req) {
        return changePasswordDAO.changePassword(req);
    }
}
