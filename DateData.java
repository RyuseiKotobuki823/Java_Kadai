package org.example;
import java.util.Calendar;

public class DateData {
    private String dateData = null;
    /**
     * dateData setter
     */
    public void setDateData(String yyyymmdd) {
        dateData = yyyymmdd;
    }

    /**
     * dateData getter
     */
    public String getDateData() {
        return dateData;
    }
    /**
     * 今日の日付取得 */
    public String getToday() {
        Calendar cal = Calendar.getInstance();
        return String.format("%02d", cal.get(Calendar.YEAR)) +
                (String.format("%02d", cal.get(Calendar.MONTH) + 1)) +
                String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 今日か否かの判定 */
    public boolean isToday(String yyyymmdd) {
        if(yyyymmdd.equals(getToday()) ){
            return true;
        }
        return false;
    }
    /**
     * うるう年判定 String */
    public boolean isLeapYear(String yyyymmdd) {
        return isLeapYear(Integer.parseInt(yyyymmdd.substring(0,4)));
    }

    /**
     * うるう年判定 int */
    public boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else return year % 4 == 0;
    }

}
