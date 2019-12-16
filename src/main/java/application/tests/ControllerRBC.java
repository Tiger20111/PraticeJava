package application.tests;

import application.tests.bd.DollarRate;
import application.tests.bd.DollarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class ControllerRBC {

  @Autowired
  private DollarRepository dollarRepository;

  public ControllerRBC() {
    this.service = new ServiceRBC();
  }

  public ControllerRBC(ServiceRBC service) {
    this.service = service;
  }

  @RequestMapping("/course/max")
  public Double getMaxCourse() {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return service.parseRequest(body);
  }

  @RequestMapping("/course/upload")
  public int uploadDataBaseDollars() throws Exception {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return service.saveMonthDollars(body, dollarRepository);
  }

  @RequestMapping("/course")
  public String printHistoric() throws ParseException {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return body;
  }

  private final ServiceRBC service;
}



