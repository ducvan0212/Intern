package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class JobSeeker extends Model {
		
	// Variables
	
	public String email;
	public String password;
	
	@Lob
	public String aboutMe;
	
	public String college;
	public String fullName;
	public Date birthday;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	public List<Resume> resumes;

	
	
	
	// Constructors
	
	public JobSeeker(String email, String password) {
		this.email = email;
		this.password = password;
		this.resumes = new ArrayList<Resume>();
	}

	public JobSeeker(String email, String password, String aboutMe, String college, String fullName) {
		this(email, password);
		this.aboutMe = aboutMe;
		this.college = college;
		this.fullName = fullName;
	}
	
	
	
	// Methods
	
	public JobSeeker addResume(String name) {
		Resume newResume = new Resume(this, name).save();
		this.resumes.add(newResume);
		this.save();
		return this;
	}
	
	
	
	// Static methods
	
	public static JobSeeker connect(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	
}
