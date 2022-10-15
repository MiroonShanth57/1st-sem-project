package Model;

public class SystemUser {
    private String RoleID;
    private String UserName;
    private String Password;

    public SystemUser() {
    }

    public SystemUser(String roleID, String userName, String password) {
        RoleID = roleID;
        UserName = userName;
        Password = password;
    }

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String roleID) {
        RoleID = roleID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
