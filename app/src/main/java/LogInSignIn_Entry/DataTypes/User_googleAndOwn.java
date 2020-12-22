package LogInSignIn_Entry.DataTypes;

public class User_googleAndOwn {

    private Google_User_Details Google;
    private CreateAccount_userDetails user_details;

    public User_googleAndOwn() {}

    public User_googleAndOwn(Google_User_Details google, CreateAccount_userDetails user_details) {
        this.Google = google;
        this.user_details = user_details;
    }

    public Google_User_Details getGoogle() {
        return Google;
    }

    public CreateAccount_userDetails getUser_details() {
        return user_details;
    }
}
