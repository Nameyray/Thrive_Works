package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import parameter_resolver.PatientParameterResolver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PatientParameterResolver.class)
class PatientTest {
  @Test
  @DisplayName("Test that a patient is instantiated correctly")
  public void newPatient_instantiatesCorrectly_true(Patient patient) {
    assertNotNull(patient);
  }

  @Test
  @DisplayName("Test that a Patient class instance instantiates with a name")
  public void newPatient_instantiatesWithName_true(Patient patient) {
    assertEquals("Jane Doe", patient.getName());
  }

  @Test
  @DisplayName("Test that a Patient class instance instantiates with an address")
  public void newPatient_instantiatesWithPhone_true(Patient patient) {
    assertEquals("Dagoretti, Nairobi", patient.getAddress());
  }

  @Test
  @DisplayName("Test that a patient class instance instantiates with an email")
  public void newPatient_instantiatesWithEmail_true(Patient patient) {
    assertEquals("jdoe@gmail.com", patient.getEmail());
  }

  @Test
  @DisplayName("Test that a patient class instantiates with a password")
  public void newPatient_instantiatesWithPassword(Patient patient) {
    assertEquals("12345678", patient.getPassword());
  }

  @Test
  @DisplayName("Test that a patient's id is set as specified")
  public void setId_setsPatientId_true(Patient patient) {
    patient.setId(1);
    assertEquals(1, patient.getId());
  }

  @Test
  @DisplayName("Test that a patient's name is set as specified")
  public void setName_setsPatientName_true(Patient patient) {
    patient.setName("Jenny Doe");
    assertEquals("Jenny Doe", patient.getName());
  }

  @Test
  @DisplayName("Test that a patient's email is set as specified")
  public void setPhone_setsPatientPhoneNo_true(Patient patient) {
    patient.setPhone(711808960);
    assertEquals(711808960, patient.getPhone());
  }

  @Test
  @DisplayName("Test that a patient's address is set as specified")
  public void setAddress_setsPatientAddress_true(Patient patient) {
    patient.setAddress("Lavington, Nairobi");
    assertEquals("Lavington, Nairobi", patient.getAddress());
  }

  @Test
  @DisplayName("Test that a patient's email is set as specified")
  public void setEmail_setsPatientEmail_true(Patient patient) {
    patient.setEmail("jennydoe@gmail.com");
    assertEquals("jennydoe@gmail.com", patient.getEmail());
  }

  @Test
  @DisplayName("Test that a patient's password is set as specified")
  public void setPassword_setsPatientPassword_true(Patient patient) {
    patient.setPassword("123456789");
    assertEquals("123456789", patient.getPassword());
  }
}