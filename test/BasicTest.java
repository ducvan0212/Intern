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
	public void fullTest() {
		Fixtures.loadModels("data.yml");
		
		// Count things
		assertEquals(2, JobSeeker.count());
		assertEquals(2, Resume.count());
		assertEquals(1, Employer.count());
		assertEquals(0, Job.count());
		
		// Try to connect as job seekers
		JobSeeker bob = JobSeeker.connect("bob@gmail.com", "secret");
		assertNotNull(bob);
		JobSeeker steve = JobSeeker.connect("steve@gmail.com", "jobs");
		assertNotNull(steve);
		assertNull(JobSeeker.connect("jeff@gmail.com", "badpassword"));
		assertNull(Employer.connect("bob@gmail.com", "secret"));
		
		// Find all of Steve's resumes
		List<Resume> steveResumes = Resume.find("owner.email", "steve@gmail.com").fetch();
		assertEquals(2, steveResumes.size());
		assertEquals(2, steve.resumes.size());
		
		
		// Find the most recent resume
		Resume frontResume = Resume.find("order by postedAt desc").first();
		assertNotNull(frontResume);
		assertEquals("My Second Resume", frontResume.name);
		
		// Post a new resume
		bob.addResume("My Third Resume");
		assertEquals(1, bob.resumes.size());
		assertEquals(3, Resume.count());
		
		// Delete steve's 2nd resume (directly)
		Resume.deleteResume(frontResume);
		assertEquals(2, Resume.count());
		assertEquals(1, steve.resumes.size());
		
		// Delete steve's latest resume (indirectly)
		steve.removeResume(0);
		assertEquals(0, steve.resumes.size());
		assertEquals(1, Resume.count());
		
		// Delete a null resume
		Resume.deleteResume(null);
		assertEquals(0, steve.resumes.size());
		assertEquals(1, Resume.count());
		
		
		Employer tom = Employer.find("byEmail", "tom@gmail.com").first();
		assertNotNull(tom);
		assertEquals(tom.contactInfo.contactEmail, "tom@gmail.com");
		assertEquals(tom.description, "We do things");
	}
}
