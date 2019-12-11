package application.tests.bd;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Utils {

  private static Integer tsToSec8601(String timestamp) {
    if (timestamp == null) return null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      Date dt = sdf.parse(timestamp);
      long epoch = dt.getTime();
      return (int) (epoch / 1000);
    } catch (ParseException e) {
      return null;
    }
  }

  public static String convertDateToString(Date date) {
    //Конвертирую в день.месяц.год
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String dateStr = dateFormat.format(date);
    StringBuilder day = new StringBuilder();
    int i = 0;
    while (dateStr.indexOf(i) != ' ') {
      if (dateStr.indexOf(i) == '-') {
        day.append('.');
      } else {
        day.append(dateStr.indexOf(i));
      }
      i++;
    }
    day.reverse();
    return day.toString();
  }

  private static String formatDateToNight(String date) {
    String numDays;
    String numMonths;
    String year;
    try {
      numDays = date.substring(0, 2);
      numMonths = date.substring(3, 5);
      year = date.substring(6, 10);
    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
      throw new NumberFormatException("Illegal format data: " + date);
    }
    return year + "-" + numMonths + "-" + numDays + "T23:59:59.000-0000";
  }

  public static Date FormatData(String date) throws ParseException {
    //подается как dd.MM.yyyy
    int numDays = 0;
    int numMonths = 0;
    int year = 0;
    numDays = Integer.parseInt(date.substring(0, 2));
    numMonths = Integer.parseInt(date.substring(3, 5));
    year = Integer.parseInt(date.substring(6, 10));
    String formalDate = Integer.toString(year) + "/" + Integer.toString(numMonths) + "/" + Integer.toString(numDays);
    return new SimpleDateFormat("yyyy/MM/dd").parse(formalDate);
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
          return Double.parseDouble(number.toString());
        }
      }

      if (inNumber) {
        number.append(symbol);
      }

    }

    return line.length() + 0.0;
  }


  public static String convertToUnix(String data) {
    String dataFormatted = formatDateToNight(data);
    int unixTime = tsToSec8601(dataFormatted);
    return Integer.toString(unixTime);
  }

}
