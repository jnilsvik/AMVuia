package dilan.prosjekt;


public class UserModel {

    private String firstName;
    private String lastName;
    private String password;
    private boolean unionMember;
    private String email;
    private String phoneNumber;
    private boolean userAdmin;

    public UserModel(String firstName, String lastName, String password, boolean unionMember, String email, boolean userAdmin) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.unionMember = unionMember;
        this.email = email;
        this.userAdmin = userAdmin;
    }

    public UserModel() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUnionMember() {
        return unionMember;
    }

    public void setUnionMember(boolean unionMember) {
        this.unionMember = unionMember;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }











}
