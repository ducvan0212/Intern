package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Job extends Model {
	
	public String name;
	public Date postedAt;
	public int requiredWorkExperience;
	
	@Lob
	public String description;
	
	@ManyToOne
	public Employer owner;
	
	public Job(Employer owner, String name) {
		this.owner = owner;
		this.name = name;
		this.postedAt = new Date();
	}
	
	public Job(Employer owner, String name, int requiredWorkExperience, String description) {
		this(owner, name);
		this.requiredWorkExperience = requiredWorkExperience;
		this.description = description;
	}
}
