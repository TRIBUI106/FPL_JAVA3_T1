package poly.com.model;

public class User 
{
	private String username;
	private String password;
	private String fullname;
	private int role;
	private String email;
	private String diachi;
	private String sdt;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public User(String username, String password, String fullname, int role, String email, String diachi, String sdt) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
		this.email = email;
		this.diachi = diachi;
		this.sdt = sdt;
	}
	public User() 
	{
		super();
	}
	
	
	

}
