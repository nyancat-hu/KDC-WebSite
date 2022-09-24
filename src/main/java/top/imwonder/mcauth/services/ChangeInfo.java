package top.imwonder.mcauth.services;

import top.imwonder.mcauth.pojo.requestbody.ForgetPasswordReq;

public interface ChangeInfo {
    public int changePassword(ForgetPasswordReq req);
}
