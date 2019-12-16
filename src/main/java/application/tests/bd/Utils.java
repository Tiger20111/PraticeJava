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

  public static void checkCorrectData(String month, String date) throws Exception {
    if (month.equals("00")) {
      throw new Exception(date);
    }
  }

  public static String convertDateToString(Date date) throws Exception {
    //Конвертирую в день.месяц.год
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    String dateStr = dateFormat.format(date);

    String year = dateStr.substring(0, 4);
    String month = dateStr.substring(5, 7);
    String day = dateStr.substring(8, 10);

    checkCorrectData(month, dateStr);
    return day + '.' + month + '.' + year;
  }

  private static String formatDateToNight(String date) throws Exception {
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
    checkCorrectData(numMonths, date);
    return year + "-" + numMonths + "-" + numDays + "T23:59:59.000-0000";
  }

  public static Date FormatData(String date) throws Exception {
    //подается как dd.MM.yyyy
    int numDays = 0;
    int numMonths = 0;
    int year = 0;
    try {
      numDays = Integer.parseInt(date.substring(0, 2));
      numMonths = Integer.parseInt(date.substring(3, 5));
      year = Integer.parseInt(date.substring(6, 10));
    } catch (Exception e) {
      throw new ParseException("Can not parse:" + date, 4);
    }
    String formalDate = Integer.toString(year) + "/" + Integer.toString(numMonths) + "/" + Integer.toString(numDays);
    checkCorrectData(date.substring(3, 5), date);
    return new SimpleDateFormat("yyyy/MM/dd").parse(formalDate);
  }

  public static String formatDateFromDollarPrint(String date) throws Exception {
    //принимает в формате yyyy-mm-dd
    String year;
    String month;
    String day;
    year = date.substring(0, 4);
    month = date.substring(6, 7);
    day = date.substring(9, 10);
    if (day.length() == 1) {
      day = '0' + day;
    }
    if (month.length() == 1) {
      if (month.equals("0")) {
        throw new Exception(date);
      }
      month = '0' + month;
    }

    return day + "/" + month + "/" + year;
  }

  public static String formatDate(String date) throws Exception {
    String year;
    String month;
    String day;
    year = date.substring(0, 3);
    month = date.substring(5, 7);
    day = date.substring(9, 11);
    checkCorrectData(month, date);
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

  public static Integer numDaysLeft(String date1, String date2) {
    int numDays1 = Integer.parseInt(date1.substring(0, 2));
    int numMonths1 = Integer.parseInt(date1.substring(3, 5));
    int year1 = Integer.parseInt(date1.substring(6, 10));

    int numDays2 = Integer.parseInt(date2.substring(0, 2));
    int numMonths2 = Integer.parseInt(date2.substring(3, 5));
    int year2 = Integer.parseInt(date2.substring(6, 10));

    int days = (year2 - year1) * 365 + (numMonths2 - numMonths1) * 30 + (numDays2 - numDays1);
    if (days < 0) {
      days = days * (-1);
    }
    return days;
  }

  public static String convertToUnix(String data) throws Exception {
    String dataFormatted = formatDateToNight(data);
    int unixTime = tsToSec8601(dataFormatted);
    return Integer.toString(unixTime);
  }

}
