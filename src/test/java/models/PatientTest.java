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
}