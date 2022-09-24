package top.imwonder.mcauth.pojo.requestbody;

public class ForgetPasswordReq {
    String email;
    String Password;

    public void setPassword(String newPassword) {
        this.Password = newPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return email;
    }
}
