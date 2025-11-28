package poly.com.model;

public class User {
    private String username;
    private String password;
    private String fullname;
    private int role; // 1 = admin, 0 = user
    private String email;
    private String address;
    private String sdt;

    public User() {}

    public User(String username, String password, String fullname, int role, String email, String address, String sdt) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.email = email;
        this.address = address;
        this.sdt = sdt;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}