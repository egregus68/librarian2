package pl.com.gregus.date.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * DateUtils.
 *
 * @author Grzegorz Guściora
 */
public final class DateUtils {

    /**
     * Format yyyy-MM-dd.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Format HH:mm:ss.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * Format HH:mm.
     */
    public static final String TIME_FORMAT_WITHOUT_SECONDS = "HH:mm";
    /**
     * Format yyyy-MM-dd HH:mm:ss.
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Format yyyy-MM-dd HH:mm.
     */
    public static final String DATE_TIME_FORMAT_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * Format yyyyMMdd_HHmmss_SSS.
     */
    public static final String DATE_TIME_FORMAT_DD_MM_YYYY = "dd-MM-yyyy HH:mm:ss";
    /**
     * Format MM/dd/yyyy.
     */
    public static final String DATE_TIME_FORMAT_MM_DD_YYYY = "MM/dd/yy";
    /**
     * Format
     */
    public static final String DATE_TIME_FILE_FORMAT = "yyyyMMdd_HHmmss_SSS";
    public static final String TIME_ZONE = "Poland";
    public static final TimeZone TIMEZONE = TimeZone.getTimeZone(TIME_ZONE);
    /**
     * Format yyyyMM.
     */
    public static final String DATA_MONTH_FORMAT = "yyyyMM";
    /**
     * Format yyyy.
     */
    public static final String DATA_YEAR_FORMAT = "yyyy";
    private static final Calendar NOW = Calendar.getInstance();
    private static Calendar temp = Calendar.getInstance();
//    private static List<Calendar> freeDayActualYear = new ArrayList<>();
    public static final int ACTUAL_YEAR = NOW.get(Calendar.YEAR);
    public static final int ACTUAL_MONTH = NOW.get(Calendar.MONTH);
    public static final int ACTUAL_DAY_OF_MONTH = NOW.get(Calendar.DAY_OF_MONTH);
//    private static List<Calendar> freeDays = new ArrayList<>();
    private static final SimpleDateFormat SDF_DEFAULT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * .
     * @param pattern
     * @param move
     * @return
     */
    public static String getDateFormatted(String pattern, int move) {
        return getDateFormatted(pattern, move, NOW);
    }

    /**
     * .
     * @param pattern
     * @param move
     * @param custom
     * @return
     */
    public static String getDateFormatted(String pattern, int move, Calendar custom) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        custom.add(Calendar.MONTH, move);
        String ret = simpleDateFormat.format(custom.getTime());
        custom.add(Calendar.MONTH, move * (-1));
        return ret;
    }

    public static int monthBaseToNo(Date monthBase) {
        Calendar tcal = new GregorianCalendar();
        tcal.setTime(monthBase);
        return (tcal.get(Calendar.YEAR) - 1996) * 12 - 12 + tcal.get(Calendar.MONTH) + 1;

    }

    public static Date noToMonthBase(int noMonthBaseOfReserve) {
        int month = noMonthBaseOfReserve % 12;
        int year = noMonthBaseOfReserve / 12 + 1997;
        temp.set(Calendar.YEAR, year);
        temp.set(Calendar.MONTH, month - 1);
        temp.set(Calendar.DAY_OF_MONTH, temp.getActualMaximum(Calendar.DAY_OF_MONTH));
        return temp.getTime();
    }

    public static int countDays(Date startD, Date endD) {

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startD);
        end.setTime(endD);

        return (int) countDaysBetween(start, end);

    }

    public static int compareMonthBase(Date monthBase, Date checkDate) {
        int yearMonthBase;
        int yearMonthCheck;
        temp.setTime(monthBase);
        yearMonthBase = temp.get(Calendar.YEAR) * 100 + temp.get(Calendar.MONTH);
        temp.setTime(checkDate);
        yearMonthCheck = temp.get(Calendar.YEAR) * 100 + temp.get(Calendar.MONTH);
        if (yearMonthBase < yearMonthCheck) {
            return 1;
        } else if (yearMonthBase == yearMonthCheck) {
            return 0;
        }
        return -1;
    }

    public static String formatDate(final Date date, final String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(date);
    }

    public static String formatDateDefault(final Date date) {
        if (date instanceof Date) {
            return SDF_DEFAULT.format(date);
        }
        return null;
    }

    public static Date getDateFromString(String Date, String pattern) throws ParseException {
        if (Date != null && !"".equals(Date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(pattern);
            return simpleDateFormat.parse(Date);
        }
        return null;

    }

    /**
     * Pobranie dni wolnych ruchomych .
     *
     * @param yearS
     * @return
     */
    public static List<Calendar> getFreeDays(int yearS) {
        //zapytania do bazy w sumie tylko raz bo w  bazie sa stale
        Calendar east = getEasterDate(yearS);
        Calendar eastMon = getEasterDate(yearS);
        Calendar eastGreen = getEasterDate(yearS);
        Calendar christ = getEasterDate(yearS);
        List<Calendar> freeDays = new ArrayList<>();
        freeDays.add(east);
        eastMon.add(Calendar.DAY_OF_YEAR, 1);
        freeDays.add(eastMon);
        //zielone swiatki
        eastGreen.add(Calendar.DAY_OF_YEAR, 49);
        freeDays.add(eastGreen);
        //boze cialo
        christ.add(Calendar.DAY_OF_YEAR, 60);
        freeDays.add(christ);
        return freeDays;
    }

    /**
     * .
     * @return
     */
    public static Calendar getCalendar() {
        return NOW;
    }

    /**
     * .
     * @return
     */
//    public static List<Calendar> getFreeDayThisYear() {
//        //zapytania do bazy
//        if (freeDayActualYear.isEmpty()) {
//            Calendar east = getEasterDate(ACTUAL_YEAR);
//            freeDayActualYear.add(east);
//            east.add(Calendar.DAY_OF_YEAR, 60);
//            freeDayActualYear.add(east);
//        }
//        return freeDayActualYear;
//    }
    /**
     * .
     * @param custom
     * @param moved
     * @param freeDays
     * @return
     */
    public static Calendar getCalendarMovedWorkDays(Calendar custom, int moved, List<Calendar> freeDays) {

        for (int i = 21; i < (moved + 20); i++) {
            boolean nextDay = false;
            if (1 == custom.get(Calendar.DAY_OF_WEEK)) {
                custom.add(Calendar.DATE, 1);
            } else if (7 == custom.get(Calendar.DAY_OF_WEEK)) {
                custom.add(Calendar.DATE, 2);
            } else {
                nextDay = true;
            }

            for (Calendar e : freeDays) {
                Calendar buf = (Calendar) e.clone();
                if (buf.get(Calendar.MONTH) == custom.get(Calendar.MONTH) && buf.get(Calendar.YEAR) == custom.get(Calendar.YEAR)
                        && buf.get(Calendar.DAY_OF_MONTH) == custom.get(Calendar.DAY_OF_MONTH)) {
                    custom.add(Calendar.DATE, 1);
                    nextDay = false;
                    break;
                }
            }

            if (nextDay) {
                custom.add(Calendar.DATE, 1);
            } else {
                i--;
            }
        }

        while (isFreeDay(custom.getTime(), freeDays)) {
            custom.add(Calendar.DATE, 1);
        }

        return custom;
    }

    /**
     * Przesuwanie daty do kolejnego(natępnego/poprzedniego) dnia roboczego
     * uwzględniając kalendarz dni wolnych.
     *
     * @param custom
     * @param moved
     * @param freeDays
     * @return
     */
    public static Calendar getMovedWorkDays(Calendar custom, int moved, List<Calendar> freeDays) {
        if (null == freeDays) {
            freeDays = new ArrayList<>();
        }
        Calendar result = (Calendar) custom.clone();
        result.add(Calendar.DAY_OF_YEAR, moved);
        boolean movedNext = false;
        while (true) {
            movedNext = false;
            for (Calendar e : freeDays) {
                Calendar buf = (Calendar) e.clone();
                if (buf.get(Calendar.MONTH) == result.get(Calendar.MONTH) && buf.get(Calendar.YEAR) == result.get(Calendar.YEAR)
                        && buf.get(Calendar.DAY_OF_MONTH) == result.get(Calendar.DAY_OF_MONTH)) {
                    movedNext = true;
                    break;
                }
            }
            if ((result.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (result.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                movedNext = true;
            }
            if (movedNext) {
                if (moved >= 0) {
                    result.add(Calendar.DAY_OF_YEAR, 1);
                } else {
                    result.add(Calendar.DAY_OF_YEAR, -1);
                }
                continue;
            }
            break;
        }

        return result;
    }

    /**
     * Spradzanie czy kolejny dzień jest dniem wolnym
     *
     * @param day
     * @param freeDays
     * @return
     */
    public static boolean isFreeNextDay(Calendar day, List<Calendar> freeDays) {
        if (null == freeDays) {
            freeDays = new ArrayList<>();
        }
        Calendar result = (Calendar) day.clone();
        result.add(Calendar.DATE, 1);
        return isFreeDay(result.getTime(), freeDays);
    }

    /**
     * Spradzanie czy podany jako paramter dzień jest dniem wolnym
     *
     * @param day
     * @param freeDays
     * @return
     */
    public static boolean isFreeDay(Date day, List<Calendar> freeDays) {
        if (null == freeDays) {
            freeDays = new ArrayList<>();
        }
        Calendar dayParam = Calendar.getInstance();
        dayParam.setTime(day);
        for (Calendar e : freeDays) {
            Calendar buf = (Calendar) e.clone();
            if (buf.get(Calendar.MONTH) == dayParam.get(Calendar.MONTH) && buf.get(Calendar.YEAR) == dayParam.get(Calendar.YEAR)
                    && buf.get(Calendar.DAY_OF_MONTH) == dayParam.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        }
        if ((dayParam.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (dayParam.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            return true;
        }

        return false;
    }

    /**
     * .
     * @param start
     * @param end
     * @return
     */
    public static long countDaysBetween(Calendar start, Calendar end) {
        int msInDay = 1000 * 60 * 60 * 24;
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        long startTime = start.getTimeInMillis();
        end.set(Calendar.HOUR_OF_DAY, 1);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        long endTime = end.getTimeInMillis();
        if (endTime < startTime) {

            endTime = startTime;
            startTime = endTime;
        }
        return ((endTime - startTime) / msInDay + 1);
    }

    /**
     * .
     * @param date
     * @param ammount
     */
    public static Date moveDateMonth(Date date, int ammount) {
        if (date != null) {
            temp.setTime(date);
            temp.add(Calendar.MONTH, ammount);
            return temp.getTime();
        }
        return null;
    }

    /**
     * .
     * @param custom
     * @param freeDayList
     * @param toBack
     */
    public static void moveDatesOnFreeDay(Calendar custom, List<Calendar> freeDays, boolean toBack) {
        if (freeDays.isEmpty()) {
            Calendar eastNextDay = getEasterDate(2013);
            eastNextDay.add(Calendar.DATE, 1);
            freeDays.add(new GregorianCalendar(2013, 0, 1));
            freeDays.add(new GregorianCalendar(2013, 0, 6));
            freeDays.add(getEasterDate(2013));
            freeDays.add(eastNextDay);
            freeDays.add(new GregorianCalendar(2013, 4, 1));
            freeDays.add(new GregorianCalendar(2013, 4, 3));
            freeDays.add(new GregorianCalendar(2013, 7, 15));
            freeDays.add(new GregorianCalendar(2013, 10, 1));
            freeDays.add(new GregorianCalendar(2013, 10, 11));
            freeDays.add(new GregorianCalendar(2013, 11, 25));
            freeDays.add(new GregorianCalendar(2013, 11, 26));
        }
        int moveSaturday = 2;
        int moveFreeDay = 1;
        int moveSunday = 1;
        if (toBack) {
            moveSaturday = -1;
            moveFreeDay = -1;
            moveSunday = -2;
        }
        boolean modiftyDate;
        do {
            if (custom.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                custom.add(Calendar.DATE, moveSunday);
            }
            if (custom.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                custom.add(Calendar.DATE, moveSaturday);
            }

            modiftyDate = false;
            for (Calendar freeDay : freeDays) {
                if (freeDay.get(Calendar.YEAR) == custom.get(Calendar.YEAR)
                        && freeDay.get(Calendar.MONTH) == custom.get(Calendar.MONTH)
                        && freeDay.get(Calendar.DAY_OF_MONTH) == custom.get(Calendar.DAY_OF_MONTH)) {
                    custom.add(Calendar.DATE, moveFreeDay);
                    modiftyDate = true;
                }
            }
        } while (modiftyDate);
    }

    /**
     * .
     * @param year
     * @return
     */
    public static String getEasterDate(String year) {
        return getDateFormatted("yyyyMMdd", 0, getEasterDate(Integer.valueOf(year)));
    }

    /**
     * .
     * @param year
     * @return
     */
    public static Calendar getEasterDate(int year) {

        int C, G, H, I, J, L, EasterMonth, EasterDay;
        G = year % 19;

        I = (19 * G + 15) % 30;
        J = (year + (year / 4) + I) % 7;

        C = year / 100;
        H = (C - C / 4 - ((8 * C + 13) / 25) + 19 * G + 15) % 30;
        I = H - (H / 28) * (1 - (H / 28) * (29 / (H + 1)) * ((21 - G) / 11));
        J = (year + (year / 4) + I + 2 - C + C / 4) % 7;

        L = I - J;
        EasterMonth = 3 + ((L + 40) / 44);
        EasterDay = L + 28 - 31 * (EasterMonth / 4);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, EasterDay);
        cal.set(Calendar.MONTH, EasterMonth - 1);
        cal.set(Calendar.YEAR, year);

        return cal;

    }

    /**
     * Przygotowanie listy data z zakresu od-do;
     *
     * @param start początek zakresu
     * @param end koniec zakresu
     * @return
     */
    public static List<Date> prepareListDate(final Date start, final Date end) {
        GregorianCalendar calendar = new GregorianCalendar();
        List<Date> result = new ArrayList<>();
        if (null == start || null == end) {
            return result;
        }
        int i = 0;
        do {
            calendar.setTime(start);
            calendar.add(calendar.DAY_OF_MONTH, i++);
            result.add(calendar.getTime());
        } while (calendar.getTime().before(end));
        return result;
    }

    /**
     * Obliczenie pierwszego dnia miesiąca .
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

//    public String getLastDayOfMonthBase(String monthReserveBase) {
//        String lastDay = "";
//        try {
//            Date monthBase = DateUtils.getDateFromString(monthReserveBase, DateUtils.DATA_MONTH_FORMAT);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(monthBase);
//
//            calendar.add(Calendar.MONTH, 1);
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            calendar.add(Calendar.DATE, -1);
//
//            Date lastDayOfMonth = calendar.getTime();
//            lastDay = DateUtils.formatDate(lastDayOfMonth, DateUtils.DATE_FORMAT);
//
//        } catch (ParseException ex) {
//            java.util.logging.Logger.getLogger(InstitutionServiceListBean.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
//        return lastDay;
//    }
    /**
     * Obliczenie ostatniego dnia miesiąca .
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Zmiana daty o days liczbę dni .
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDaysInMonth(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (!((cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DATE)) && (days > 0))
                && !((cal.get(Calendar.DATE) == cal.getActualMinimum(Calendar.DATE)) && (days < 0))) {
            cal.add(Calendar.DATE, days);
        }
        return cal.getTime();
    }

    /**
     * Przygotowanie listy data dni pracujących z zakresu od-do;
     *
     * @param start początek zakresu
     * @param end koniec zakresu
     * @param nonWorkingDays lista dni wolnych
     * @return
     */
    public static List<Date> prepareListDate(final Date start, final Date end, List<Date> nonWorkingDays) {
        //pobieramy listę dni z zkresu
        List<Date> allDate = prepareListDate(start, end);
        List<Date> workingDays = new ArrayList<>();
        Calendar call = Calendar.getInstance();
        for (Date day : allDate) {
            call.setTime(day);
            if (Calendar.SATURDAY == call.get(Calendar.DAY_OF_WEEK)
                    || Calendar.SUNDAY == call.get(Calendar.DAY_OF_WEEK)
                    || nonWorkingDays.contains(day)) {
                continue;
            }
            workingDays.add(day);
        }
        return workingDays;
    }

    /**
     * Pobieranie kolejnego dnia roboczego od daty zadanej jako parametr.
     *
     * @param today dzień od którego będzie liczony dzień następny
     * @return kolejny dzień roboczy.
     */
    public static Date getNextWorkingDay(Date today) {
        if (null == today) {
            today = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (Calendar.FRIDAY == dayOfWeek) {
            calendar.add(Calendar.DATE, 3);
        } else if (Calendar.SATURDAY == dayOfWeek) {
            calendar.add(Calendar.DATE, 2);
        } else {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar.getTime();
    }

    /**
     * Pobieranie kolejnego dnia roboczego od daty zadanej jako parametr.
     *
     * @param today dzień od którego będzie liczony dzień następny
     * @return kolejny dzień roboczy.
     */
    public static Date getLastWorkingDay(Date today) {
        if (null == today) {
            today = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (Calendar.SATURDAY == dayOfWeek) {
            calendar.add(Calendar.DATE, -1);
        } else if (Calendar.SUNDAY == dayOfWeek) {
            calendar.add(Calendar.DATE, -2);
        } else {
            calendar.add(Calendar.DATE, -1);
        }

        return calendar.getTime();
    }

    public static Date getBeginOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    public static Date copyDate(Date date) {
        if (null == date) {
            return date;
        }
        Date copy = new Date();
        copy.setTime(date.getTime());
        return copy;
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    public static Date parseDate(String date) throws ParseException {
        if (date == null || date.isEmpty()) {
            return null;
        }
        synchronized (SDF_DEFAULT) {
            return SDF_DEFAULT.parse(date);
        }
    }
}
