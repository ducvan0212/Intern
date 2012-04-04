package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Resume extends Model {
	
	public String name;
	public Date postedAt;
	public int workExperience;
	
	@Lob
	public String description;
	
	@ManyToOne
	public JobSeeker owner;
	
	public Resume(JobSeeker owner, String name) {
		this.owner = owner;
		this.name = name;
		this.postedAt = new Date();
	}
	
	public Resume(JobSeeker owner, String name, int workExperience, String description) {
		this(owner, name);
		this.workExperience = workExperience;
		this.description = description;
	}
	
	
	
	// Static methods
	public static void deleteResume(Resume resume) {
		if (resume == null) return;
		
		resume.owner.resumes.remove(resume);
		resume.delete();
	}
}
