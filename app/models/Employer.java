package models;

import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Employer extends Model {

	// Variables
	
	public String email;
	public String password;
	
	public String companyName;
	public String industry;
	
	@Lob
	public String description;
	
	@Embedded
	public ContactInfo contactInfo;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	public List<Job> jobs;
	
	
	
	
	// Constructors
	
	public Employer(String email, String password) {
		this.email = email;
		this.password = password;
		this.jobs = new ArrayList<Job>();
	}
	
	public Employer(String email, 
			String password, 
			String companyName, 
			String industry, 
			String description,
			ContactInfo contactInfo) {
		this(email, password);
		this.companyName = companyName;
		this.industry = industry;
		this.description = description;
		this.contactInfo = contactInfo;
	}
	
	
	
	// Methods
	
	public Employer addJob(String name) {
		Job newJob = new Job(this, name).save();
		this.jobs.add(newJob);
		this.save();
		return this;
	}
	
	
	
	
	// Static methods
	
	public static Employer connect(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	
}
