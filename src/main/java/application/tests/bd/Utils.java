package application.tests.bd;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Utils {

  public static Date FormatData(String date) throws ParseException {
    //подается как dd.MM.yyyy
    int numDays = 0;
    int numMonths = 0;
    int year = 0;
    numDays = Integer.parseInt(date.substring(0, 2));
    numMonths = Integer.parseInt(date.substring(3, 5));
    year = Integer.parseInt(date.substring(6, 10));
    String formalDate = Integer.toString(year) + "/" + Integer.toString(numMonths) + "/" + Integer.toString(numDays);
    Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(formalDate);
    return date1;
  }

  public static String formatDate(String date) {
    String year;
    String month;
    String day;
    year = date.substring(0, 3);
    month = date.substring(5, 7);
    day = date.substring(9, 11);
    return day + "." + month + "." + year;
  }

  public static Double getDoubleFromString(String line, int startIndex) {
    StringBuilder number = new StringBuilder();
    boolean inNumber = false;
    for(int i = startIndex; i < line.length(); i++) {
      char symbol = line.charAt(i);

      if (Character.isDigit(symbol) && (!inNumber)) {
        inNumber = true;

      } else {
        if (inNumber && !Character.isDigit(symbol)) {
          if(symbol == '.') {
            number.append(symbol);
            continue;
          }
          //number = number.reverse();
          return Double.parseDouble(number.toString());
        }
      }

      if (inNumber) {
        number.append(symbol);
      }

    }

    return line.length() + 0.0;
  }

  private String convertToUnix(String data) {
    //Тип будет 23.10.1998 такого вида
    int numDays = 0;
    int numMonths = 0;
    int year = 0;
    try {
      numDays = Integer.parseInt(data.substring(0, 2));
      numMonths = Integer.parseInt(data.substring(3, 5));
      year = Integer.parseInt(data.substring(6, 10));
      if (year != currentYear) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
      throw new NumberFormatException("Illegal format data: " + data + " " + Integer.toString(numDays) + " " + Integer.toString(numMonths) + " " + Integer.toString(year));
    }
    int unixTime = startUnixYear + numDays * secDay + getUnixTilMonth(numMonths);
    return Integer.toString(unixTime);
  }

}
