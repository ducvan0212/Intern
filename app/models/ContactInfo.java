package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Embeddable
public class ContactInfo {
	
	public String contactEmail;
	public String mobile;
	public String phone;
	
	public ContactInfo(String email) {
		this.contactEmail = email;
	}
	
	public ContactInfo(String email,
			String mobile,
			String phone) {
		this.contactEmail = email;
		this.mobile = mobile;
		this.phone = phone;
	}
}
