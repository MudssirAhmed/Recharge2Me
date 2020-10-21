package LogInSignIn_Entry;

public class Google_User_Details {
    final String Google_UID;
    final String Google_Profile;

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
