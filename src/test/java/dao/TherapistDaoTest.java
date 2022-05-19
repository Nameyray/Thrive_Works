package dao;

import models.Therapist;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import parameter_resolver.TherapistParameterResolver;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TherapistParameterResolver.class)
class TherapistDaoTest {
  private static TherapistDao therapistDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive_test", "anna", "pol1234");
    therapistDao = new TherapistDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a therapist can be added")
  public void add_addsPatient_true(Therapist therapist) {
    therapistDao.add(therapist);
    assertTrue(therapistDao.findAll().contains(therapist));
  }

  @Test
  @DisplayName("Test that a therapist's id is set upon insertion")
  public void add_setsTherapistId_true(Therapist therapist) {
    int initialId = therapist.getId();
    therapistDao.add(therapist);
    assertNotEquals(initialId, therapist.getId());
  }

  @Test
  @DisplayName("Test that a list of therapists can be retrieved")
  public void findAll_retrievesPatientList_true(Therapist therapist) {
    therapistDao.add(therapist);
    Therapist[] therapists = {therapist};
    assertEquals(Arrays.asList(therapists), therapistDao.findAll());
  }

  @Test
  @DisplayName("Test that an empty list is retrieved if no therapists listed")
  public void findAll_retrievesEmptyListIfNoPatients_true() {
    assertEquals(0, therapistDao.findAll().size());
  }

  @Test
  @DisplayName("Test that a therapist's data can be updated")
  public void update_updatesPatientData_true(Therapist therapist) {
    therapistDao.add(therapist);
    therapist.setName("Jerry doe");
    therapist.setPhone(709765123);
    therapist.setAddress("Kileleshwa, Nairobi");
    therapist.setSpecialization("Depression");
    therapist.setRatings(4);
    therapist.setIs_vetted(1);
    therapistDao.update(therapist);
    assertEquals(therapist, therapistDao.findById(therapist.getId()));
  }

  @Test
  @DisplayName("Test that a therapist's data can be deleted")
  public void delete_deletesPatientData_false(Therapist therapist) {
    therapistDao.add(therapist);
    therapistDao.delete(therapist.getId());
    assertFalse(therapistDao.findAll().contains(therapist));
  }

  @Test
  @DisplayName("Test that all therapists' data can be deleted")
  public void delete_deletesAllTherapistsData_true(Therapist therapist) {
    therapistDao.add(therapist);
    therapistDao.delete();
    assertEquals(0, therapistDao.findAll().size());
  }

  @Test
  @DisplayName("Test that a therapist's rating can be computed correctly")
  public void computeRating_computesTherapistRating_true(Therapist therapist) {
    therapistDao.add(therapist);
    therapistDao.computeRating(therapist.getId(), 4);
    Therapist foundTherapist = therapistDao.findById(therapist.getId());
    assertEquals(4, foundTherapist.getRatings());
  }

  @AfterEach
  public void tearDown() {
    therapistDao.delete();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}