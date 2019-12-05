package application.tests;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class ControllerPredictCourse {
  public ControllerPredictCourse() {
    this.service = new ServicePredictCourse();
  }
  public ControllerPredictCourse(ServicePredictCourse service) {
    this.service = service;
  }

  @RequestMapping(value = "/predict/{data}")
  public Double getWeather(@PathVariable("data") String data) throws ParseException {
    return 0.0;
  }
  private final ServicePredictCourse service;
}
