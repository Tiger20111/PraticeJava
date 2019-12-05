package application.tests;

import application.tests.bd.DollarRate;
import application.tests.bd.DollarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

import static application.tests.bd.Utils.formatDate;

class ServiceRBC {
  @Autowired
  private DollarRepository dollarRepository;

  ServiceRBC() {
    this.restTemplate = new RestTemplate();
  }

  String getData() {
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    return response.getBody();
  }

  int saveMonthDollars(String body) throws ParseException {
    String[] lines = body.split("\n");
    double sumDollars = 0;
    int numDays = 0;
    String date = "";

    for (String line : lines) {
      String[] words = " ".split(line);
      for (String word : words) {
        if (isNewDate(word)) {
          if (numDays == 0) {
            date = word;
          } else {
            DollarRate dollarRate = new DollarRate(formatDate(date), (sumDollars / numDays));
            dollarRepository.save(dollarRate);
            sumDollars = 0;
            numDays = 0;
            date = word;
          }
        } else {
          if (isRateDollar(word)) {
            numDays += 1;
            sumDollars += Double.parseDouble(word);
          }
        }
      }
    }

    if (numDays != 0) {
      DollarRate dollarRate = new DollarRate(formatDate(date), (sumDollars / numDays));
      dollarRepository.save(dollarRate);
    }
    return -1; /////////////////////////////////
  }

  private Boolean isRateDollar(String word) {
    int numPoints = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == '.') {
        numPoints++;
      }
    }
    return numPoints == 1;
  }

  private Boolean isNewDate(String word) {
    int numPoints = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == '.') {
        numPoints++;
      }
    }
    return numPoints == 2;
  }

  Double parseRequest(String body) {
    String[] lines = body.split("\n");

    double maxCourse = -1;
    for (String line : lines) {
      String[] parts = line.split("\\s");
      double currentCourse = Double.parseDouble(parts[parts.length - 1]);

      if (maxCourse < currentCourse || maxCourse == -1) {
        maxCourse = currentCourse;
      }
    }

    return maxCourse;
  }

  private String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&d1=01&m1=07&y1=2019&d2=01&m2=10&y2=2019&separator=TAB&data_format=BROWSER";
  private final RestTemplate restTemplate;
}
