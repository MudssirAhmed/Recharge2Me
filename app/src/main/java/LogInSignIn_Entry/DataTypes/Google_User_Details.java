package LogInSignIn_Entry.DataTypes;

public class Google_User_Details {


    private String Google_UID;
    private String Google_Profile;

    public Google_User_Details() {}

    public Google_User_Details(String google_UID, String google_Profile) {
        Google_UID = google_UID;
        Google_Profile = google_Profile;
    }

    public String getGoogle_UID() {
        return Google_UID;
    }

    public String getGoogle_Profile() {
        return Google_Profile;
    }
}
