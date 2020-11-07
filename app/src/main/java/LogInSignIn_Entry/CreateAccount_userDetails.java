package LogInSignIn_Entry;

public class CreateAccount_userDetails {
    final String Name;
    final String Email;


    public CreateAccount_userDetails(String name, String email) {
        Name = name;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

}
