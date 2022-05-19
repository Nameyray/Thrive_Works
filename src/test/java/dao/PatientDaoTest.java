package dao;

import models.Patient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import parameter_resolver.PatientParameterResolver;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PatientParameterResolver.class)
class PatientDaoTest {
  private static PatientDao patientDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive_test", "anna", "pol1234");
    patientDao = new PatientDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a patient can be added")
  public void add_addsPatient_true(Patient patient) {
    patientDao.add(patient);
    assertTrue(patientDao.findAll().contains(patient));
  }

  @Test
  @DisplayName("Test that a patient's id is set upon insertion")
  public void add_setsPatientId_true(Patient patient) {
    int initialId = patient.getId();
    patientDao.add(patient);
    assertNotEquals(initialId, patient.getId());
  }

  @Test
  @DisplayName("Test that a list of patients can be retrieved")
  public void findAll_retrievesPatientList_true(Patient patient) {
    patientDao.add(patient);
    Patient[] patients = {patient};
    assertEquals(Arrays.asList(patients), patientDao.findAll());
  }

  @Test
  @DisplayName("Test that an empty list is retrieved if no patients listed")
  public void findAll_retrievesEmptyListIfNoPatients_true() {
    assertEquals(0, patientDao.findAll().size());
  }

  @Test
  @DisplayName("Test that patient's data can be updated")
  public void update_updatesPatientData_true(Patient patient) {
    patientDao.add(patient);
    patient.setName("Jerry doe");
    patient.setPhone(709765123);
    patient.setAddress("Kileleshwa, Nairobi");
    patientDao.update(patient);
    assertEquals(patient, patientDao.findById(patient.getId()));
  }

  @Test
  @DisplayName("Test that a patient's data can be deleted")
  public void delete_deletesPatientData_false(Patient patient) {
    patientDao.add(patient);
    patientDao.delete(patient.getId());
    assertFalse(patientDao.findAll().contains(patient));
  }

  @Test
  @DisplayName("Test that all patients' data can be deleted")
  public void delete_deletesAllPatientsData_true(Patient patient) {
    patientDao.add(patient);
    patientDao.delete();
    assertEquals(0, patientDao.findAll().size());
  }

  @Test
  @DisplayName("Test that a patient can be logged in if correct authentication credentials are provided")
  public void login_logsInPatientIfCorrectCredentials_true(Patient patient) {
    patientDao.add(patient);
    Map<String,Object> result = patientDao.login(patient.getEmail(), patient.getPassword());
    assertTrue((Boolean) result.get("login"));
    assertEquals(patient, result.get("current"));
  }

  @Test
  @DisplayName("Test that a patient is not logged in if incorrect authentication credentials are provided")
  public void login_doesNotLoginPatientIfIncorrectCredentials_false(Patient patient) {
    patientDao.add(patient);
    Map<String,Object> result = patientDao.login("ldoe@gmail.com", "123");
    assertFalse((Boolean) result.get("login"));
  }

  @Test
  @DisplayName("Test that a patient can signup")
  public void signUp_signsUpPatient_true(Patient patient) {
    patientDao.signup(patient);
    assertTrue(patientDao.findAll().contains(patient));
  }

  @AfterEach
  public void tearDown() {
    patientDao.delete();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}