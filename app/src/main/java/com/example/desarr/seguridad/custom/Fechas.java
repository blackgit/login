package com.example.desarr.seguridad.custom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fechas {

    public static String getMovilDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }

    public static String getMovilHour() {
        return DateFormat.getTimeInstance().format(new Date());
    }
}
