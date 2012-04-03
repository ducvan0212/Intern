import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}
	
	@Test
    public void createAndRetrieveJobSeeker() {
    	// Create a new job seeker and save it
    	new JobSeeker("bob@gmail.com", "secret").save();
    	
    	// Retrieve the job seeker with e-mail address bob@gmail.com
    	JobSeeker bob = JobSeeker.find("byEmail", "bob@gmail.com").first();    
    	
    	// Test
    	assertNotNull(bob);
    	assertEquals(1, JobSeeker.count());
    	
    	new JobSeeker("steve@gmail.com", "jobs", "I'm Jobs", "Bates", "Steve Jobs").save();
    	JobSeeker jobs = JobSeeker.find("byEmail", "steve@gmail.com").first();
    	assertNotNull(jobs);
    	assertEquals(jobs.fullName, "Steve Jobs");
    	assertNotSame(jobs.aboutMe, "Just fun");
    }
	
	@Test
    public void tryConnectAsJobSeeker() {
    	// Create a new job seeker and save it
    	new JobSeeker("bob@gmail.com", "secret").save();
    	new JobSeeker("steve@gmail.com", "jobs", "I'm Jobs", "Bates", "Steve Jobs").save();
 
    	assertEquals(2, JobSeeker.count());
    	
    	// Test
    	assertNotNull(JobSeeker.connect("bob@gmail.com", "secret"));
    	assertNotNull(JobSeeker.connect("steve@gmail.com", "jobs"));
    	assertNull(JobSeeker.connect("bob@gmail.com", "badpassword"));
    	assertNull(JobSeeker.connect("tom@gmail.com", "secret"));
    }
    
	@Test
	public void createAndRetrieveEmployer() {
		// Create a new employer and save it
		new Employer("john@yahoo.com", "confidential").save();
		
		// Retrieve the employer with e-mail address "john@yahoo.com"
		Employer john = Employer.find("byEmail", "john@yahoo.com").first();
		
		// Test
		assertNotNull(john);
		assertEquals(1, Employer.count());
	}
	
	@Test
	public void tryConnectAsEmployer() {
		// Create a new employer and save it
		new Employer("john@yahoo.com", "confidential").save();
		
		assertNotNull(Employer.connect("john@yahoo.com", "confidential"));
		assertNull(Employer.connect("bob@gmail.com", "secret"));
	}
	
	@Test
	public void useTheResumeRelation() {
		// Create a new job seeker and save it
		JobSeeker bob = new JobSeeker("bob@gmail.com", "secret").save();
		
		// Create new resume
		bob.addResume("My First Resume");
		bob.addResume("My Second Resume");
		
		// Count things
		assertEquals(1, JobSeeker.count());
		assertEquals(2, Resume.count());
		
		// Retrieve Bob's resume
		Resume firstResume = Resume.find("byOwner", bob).first();
		assertNotNull(firstResume);
		assertEquals(firstResume.name, "My First Resume");
		assertEquals(bob.resumes.get(0).name, "My First Resume");
		
		/* Problem when deleting on the other side, not solved yet
		// Delete the resume
		firstResume.delete();
		
		// Check that the resume have been deleted
		assertEquals(1, Resume.count());
		*/
		
		// Delete the user
		bob.delete();
		
		// Check that all the resume have been deleted
		assertEquals(0, Resume.count());
		assertEquals(0, JobSeeker.count());
	}
}
