package com.vanhack.jobmatch.infra.rotines;

import java.text.SimpleDateFormat;

public class DateFormatter {

	public static String format(java.util.Date data) {
        return format(data, "MM/dd/yyyy");
    }

    public static String format(java.util.Date data,
                                                String formato) {
        String dataConvertida = null;

        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        dataConvertida = formatador.format(data);

        return dataConvertida;
    }
}
