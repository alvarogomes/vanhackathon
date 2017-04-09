package com.vanhack.jobmatch.useful;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

	public static String format(java.util.Date data) {
		if (data == null ) return  "";
        return format(data, "MM/dd/yyyy");
    }

    public static String format(java.util.Date data,
                                                String formato) {
        String dataConvertida = null;

        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        dataConvertida = formatador.format(data);

        return dataConvertida;
    }
    
    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
            (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
    
}
