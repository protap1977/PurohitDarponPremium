package adhikariit.blogspot.purohitdarponpremium;

public class User {
    private String name;
    private String email;
    private String password;
    private String dateofBirth;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String email, String password, String dateofBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateofBirth = dateofBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateofBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateofBirth = dateOfBirth;
    }
}
