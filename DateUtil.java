import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateUtil {


    //prac10 getDaysBetweenをcountDaysBetweenにrename
    public static int countDaysBetween(Date from, Date to) {
        int i = 0;
        while (!addDaysToDate(from, i).equals(to)) {
            i++;
        }
        return i;
    }
    //与えられた日付が平日(月火水木金のいずれか)か否かを判定するプログラムを作成してください
    //isWeekDay
    //与えられた日付が休日(土日または祝日)か否かを判定するプログラムを作成してください
    //isHolidayまたはisSaturdayOrSunday
    //ある期間内の平日の数をカウントするプログラムを作成してください
    //countWeekDaysBetween
    //指定された日付から次の平日を計算するプログラムを作成してください
    //getNextWeekDay
    //intの配列に対して合計時間を計算し返すメソッドを作成してください
    //sum

    //prac12
    /**
     * 指定日が月曜から金曜かどうかを返す
     *
     * @param yyyymmdd format "YYYYMMDD".
     * @return {@code true} もし引数の日が月曜から金曜なら, {@code false} その他
     * @throws ParseException 日付がYYYYMMDDの形式でない時
     */
    public static boolean isWeekDay(String yyyymmdd) throws ParseException {
        final Calendar calendar = getCalendarFor(yyyymmdd);
        final int TUESDAY_CODE = 2;
        final int FRIDAY_CODE = 6;
        final int dayOfWeekCode = calendar.get(Calendar.DAY_OF_WEEK);
        if(TUESDAY_CODE <= dayOfWeekCode && dayOfWeekCode <= FRIDAY_CODE ) {
            return true;
        } else {
            return false;
        }
    }

    //prac13
    public static boolean isSaturdayOrSunday(String yyyymmdd) throws ParseException {
        return !isWeekDay(yyyymmdd);
    }

    //prac14
    public static String[] getNationalHoliday(int yyyy) throws URISyntaxException, IOException, InterruptedException  {
        final String responseBody = getResponseBodyTo("https://holidays-jp.github.io/api/v1/" + yyyy + "/date.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(responseBody);
        return getKeyStringArr(jsonNode);
    }

    //prac14
    private static String[] getKeyStringArr(JsonNode jsonNode) {
        final int size = jsonNode.size();
        final String[] arr = new String[size];
        final Iterator<String> iterator = jsonNode.fieldNames();
        for (int i = 0; iterator.hasNext(); i++) {
            arr[i] = iterator.next().replace("-", "/");
        }
        return arr;
    }

    //prac14
    public static String getResponseBodyTo(String url) throws URISyntaxException, IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    //prac15
    public static boolean isNationalHoliday(String yyyymmdd) throws URISyntaxException, IOException, InterruptedException {
        final int year = getYearOf(yyyymmdd);
        final String[] nationalHolidays = getNationalHoliday(year);
        final ArrayList<String> arrDates = new ArrayList<>(Arrays.asList(nationalHolidays));
        return arrDates.contains(yyyymmdd);
    }

    private static int getYearOf(String yyyymmdd) {
        return Integer.parseInt(yyyymmdd.substring(0, 4));
    }

    private static Calendar getCalendarFor(String yyyymmdd) throws ParseException {
        final Date date = DateUtil.validateAndParseDate(yyyymmdd);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //prac16
    public static boolean isHoliday(String yyyymmdd) throws URISyntaxException, IOException, InterruptedException, ParseException {
        return isNationalHoliday(yyyymmdd) || isSaturdayOrSunday(yyyymmdd);
    }

    public static Date validateAndParseDate(String inputDate) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);
        return sdf.parse(inputDate);
    }

    public static Date addDaysToDate(Date date, int days) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

}