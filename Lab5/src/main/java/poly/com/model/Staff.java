package poly.com.model;

import java.util.Date;

public class Staff {
	private String fullname;
	private String photo_file;
	private Date birthday;
	private boolean gender;
	private boolean married;
	private String country;
	private String[] hobbies;
	private String note;
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhoto_file() {
		return photo_file;
	}
	public void setPhoto_file(String photo_file) {
		this.photo_file = photo_file;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public boolean isMarried() {
		return married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Staff(String fullname, String photo_file, Date birthday, boolean gender, boolean married, String country,
			String[] hobbies, String note) {
		super();
		this.fullname = fullname;
		this.photo_file = photo_file;
		this.birthday = birthday;
		this.gender = gender;
		this.married = married;
		this.country = country;
		this.hobbies = hobbies;
		this.note = note;
	}
	public Staff() {
		super();
	}

	
}
