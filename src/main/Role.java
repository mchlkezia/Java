package main;

public class Role {
	
	String id, email, pass, gender, nationality;
	Integer age;
	private String type;
	
	public Role(String id, String email, String pass, String gender, String nationality, Integer age) {
		this.id = id;
		this.email = email;
		this.pass = pass;
		this.gender = gender;
		this.nationality = nationality;
		this.age = age;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Role(String type) {
        this.type = type;
    }
	
    public String getType() {
        return type;
    }
	
}
