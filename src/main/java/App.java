import dao.AppointmentDao;
import dao.PatientDao;
import dao.TherapistDao;
import models.Appointment;
import models.Patient;
import models.Therapist;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public/");
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive", "anna", "pol1234");
        PatientDao patientDao = new PatientDao(sql2o);
        TherapistDao therapistDao = new TherapistDao(sql2o);
        AppointmentDao appointmentDao = new AppointmentDao(sql2o);

        get("/", (request, response) -> new ModelAndView(new HashMap<>(),"welcome.hbs"),new HandlebarsTemplateEngine());

        get("/login/new", (request, response) -> new ModelAndView(new HashMap<>(), "login.hbs"), new HandlebarsTemplateEngine());

        get("/signup/new", (request, response) -> new ModelAndView(new HashMap<>(), "signup.hbs"), new HandlebarsTemplateEngine());

        post("/login", (request, response) -> {
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            if((Boolean) patientDao.login(email, password).get("login")){
                request.session().attribute("current", patientDao.login(email, password).get("current"));
                response.redirect("/survey/new");
            } else {
                response.redirect("/");
                // If authentication fails
            }
            return null;
        });

        post("/signup", (request, response) -> {
            String name = request.queryParams("name");
            int phone = parseInt(request.queryParams("phone"));
            String address = request.queryParams("address");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            Patient patient = new Patient(name, phone, address, email, password);
            patient.setRole("Patient");
            patientDao.signup(patient);
            response.redirect("/login/new");
            return null;
        });

        get("/survey/new", (request, response) -> new ModelAndView(new HashMap<>(), "surveyform.hbs"), new HandlebarsTemplateEngine());

        post("/survey", (request, response) -> {
            String gender = request.queryParams("gender");
            String age = request.queryParams("age");
            String relationship = request.queryParams("relationship");
            String seenTherapist = request.queryParams("seen_therapist");
            String physicalHealth = request.queryParams("physical_health");
            String eatingHabits = request.queryParams("eating_habits");
            String financialStatus = request.queryParams("financial_status");
            String language = request.queryParams("language");
            String preferred = request.queryParams("preferred");
            System.out.println(gender + age + relationship + seenTherapist + physicalHealth + eatingHabits + financialStatus + language + preferred);
            request.session().attribute("responses", patientDao.captureSurveyResponses(gender, age, relationship, seenTherapist, physicalHealth, eatingHabits, financialStatus, language, preferred));
            response.redirect("/therapists");
            return null;
        });

        get("/therapists", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("therapists", therapistDao.selectTherapists(request.session().attribute("responses")));
            return new ModelAndView(model, "therapistlist.hbs");
        }, new HandlebarsTemplateEngine());

        get("/therapists/:id/selected", (request, response) -> {
            Therapist therapist = therapistDao.findById(parseInt(request.params("id")));
            Patient patient = request.session().attribute("current");
            Appointment appointment = new Appointment(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), patient.getId(), therapist.getId(), "");
            System.out.println("Patient: " + appointment.getPatient() + " Therapist: " + appointment.getTherapist());
            appointmentDao.add(appointment);
            response.redirect("/patient/home");
            return null;
        });

        get("/patient/home", (request, response) -> {
            Patient patient = request.session().attribute("current");
            Map<String, Object> model = new HashMap<>();
            model.put("patient", patient);
            return new ModelAndView(model, "patienthome.hbs");
        }, new HandlebarsTemplateEngine());
    }
}

