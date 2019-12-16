package application.tests;

import application.tests.bd.DollarRate;
import application.tests.bd.DollarRepository;
import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static application.tests.bd.Utils.FormatData;
import static application.tests.bd.Utils.convertDateToString;
import static application.tests.bd.Utils.numDaysLeft;

@Component
public class ServicePredictCourse {
  ServicePredictCourse() {

  }

  Double makePrediction(String data, DollarRepository dollarRepository, WeatherRepository weatherRepository, ServiceRBC serviceRBC, ServiceWeather serviceWeather) throws Exception {
    if (dollarRepository.count() == 0) {
      String body = serviceRBC.getData();
      if (body == null) {
        throw new RuntimeException("404");
      }
      serviceRBC.saveMonthDollars(body, dollarRepository);
      DollarRate dollarRate = dollarRepository.findByData(FormatData(data));
      if (dollarRate != null) {
        return dollarRate.getPercentage();
      }
    }
    if (dollarRepository.count() == 0) {
      return -1.0;
    }


    ArrayList<DependencyDollarWeather> dependencyDollarWeathers = new ArrayList<>();

    Iterable<DollarRate> dollarRates = dollarRepository.findAll();
    for (DollarRate dollarRate: dollarRates) {
      Double rateDollar = dollarRate.getPercentage();
      Date dateDollar = dollarRate.getData();
      String convertedDate = convertDateToString(dateDollar);
      Double percentageWeather = serviceWeather.getTemperatureDate(convertedDate, weatherRepository);
      DependencyDollarWeather dependencyDollarWeather = new DependencyDollarWeather(convertedDate, rateDollar, percentageWeather);
      dependencyDollarWeathers.add(dependencyDollarWeather);
    }
    return analyseByDate(data, dependencyDollarWeathers);

  }

  private Double analyseByDate(String date, ArrayList<DependencyDollarWeather> dependencyDollarWeathers) throws Exception {
    Double sum = 0.0;
    if (dependencyDollarWeathers.size() == 0) {
      return -1.0;
    }
    int koef = 0;
    Double averageDifference = 0.0;
    for (int i = 0; i < dependencyDollarWeathers.size() - 1; i++) {
      averageDifference += dependencyDollarWeathers.get(i + 1).getPercentage() - dependencyDollarWeathers.get(i).getPercentage();

      koef = 1;
      Random random = new Random();
      int randomBool = random.nextInt(1);
      if (randomBool == 0) {
        koef *= -1;
      }
      averageDifference = averageDifference / dependencyDollarWeathers.size();
    }
    return dependencyDollarWeathers.get(dependencyDollarWeathers.size() - 1).getPercentage()
            + koef * averageDifference *numDaysLeft(date, dependencyDollarWeathers.get(dependencyDollarWeathers.size() - 1).getDate());

  }

}
