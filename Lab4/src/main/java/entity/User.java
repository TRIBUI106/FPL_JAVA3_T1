package entity;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String gender;
    private boolean haveFamily;
    private String country;
    private List<String> hobby;
    private String note;
	
    
    
    public User(String username, String password, String gender, boolean haveFamily, String country, List<String> hobby,
			String note) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.haveFamily = haveFamily;
		this.country = country;
		this.hobby = hobby;
		this.note = note;
	}
    
	public User() {
		super();
	}

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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isHaveFamily() {
		return haveFamily;
	}
	public void setHaveFamily(boolean haveFamily) {
		this.haveFamily = haveFamily;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<String> getHobby() {
		return hobby;
	}
	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

    
	
    
}