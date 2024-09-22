package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains unit test for the Department class to validate  
 * its functionality, including methods and the constructor. 
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Set up department instance for testing. 
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    testCourses = new HashMap<>();
    Course coms4111 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourses.put("4111", coms4111);
    testDepartment = new Department("COMS", testCourses, "Paul Blaer", 3000);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "COMS 4111: \n"
            + "Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(3000, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Paul Blaer", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(testCourses, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(3001, testDepartment.getNumberOfMajors());
    testDepartment.dropPersonFromMajor();
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(2999, testDepartment.getNumberOfMajors());
    testDepartment.addPersonToMajor();
  }


  /** The test courses instance used for testing. */
  public static HashMap<String, Course> testCourses;
  
  /** The test department instance used for testing. */
  public static Department testDepartment;
}

