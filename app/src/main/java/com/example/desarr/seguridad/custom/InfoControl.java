package com.example.desarr.seguridad.custom;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class InfoControl extends Application {

    public static String getData(){
        String data =
            "SERIAL: " + Build.SERIAL + ".." +
            "MODEL: " + Build.MODEL + ".." +
            "ID: " + Build.ID + ".." +
            "Manufacture: " + Build.MANUFACTURER + ".." +
            "SDK:  " + Build.VERSION.SDK;
        return data;
    }

    public static String getDataDev(){
        /*
        "SERIAL: " + Build.SERIAL + "\n" +
        "MODEL: " + Build.MODEL + "\n" +
        "ID: " + Build.ID + "\n" +
        "Manufacture: " + Build.MANUFACTURER + "\n" +
        "Brand: " + Build.BRAND + "\n" +
        "Type: " + Build.TYPE + "\n" +
        "User: " + Build.USER + "\n" +
        "BASE: " + Build.VERSION_CODES.BASE + "\n" +
        "INCREMENTAL: " + Build.VERSION.INCREMENTAL + "\n" +
        "SDK:  " + Build.VERSION.SDK + "\n" +
        "BOARD: " + Build.BOARD + "\n" +
        "BRAND: " + Build.BRAND + "\n" +
        "HOST: " + Build.HOST + "\n" +
        "FINGERPRINT: " + Build.FINGERPRINT + "\n" +
        "Version Code: " + Build.VERSION.RELEASE;
        */
        return "SERIAL:" + Build.SERIAL+"MODEL:" + Build.MODEL;
    }

    public static String getInfo1(Context context, Activity activity) {
        StringBuffer dataMovil = new StringBuffer();
        Context aContext = context;
        String values;
        String myDeviceModel = android.os.Build.MODEL;
        String serviceName = context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
        String IMEI, IMSI, OPNM;
        //TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        //String imei = telephonyManager.getDeviceId();
        try {
            //int permissionCheck = ContextCompat.checkSelfPermission(activity,
            //        Manifest.permission.WRITE_CALENDAR);
            //IMEI = m_telephonyManager.getDeviceId();
            //IMSI = m_telephonyManager.getSubscriberId();
            //OPNM = m_telephonyManager.getNetworkOperatorName();
            dataMovil.append(myDeviceModel + " -- ");
            dataMovil.append(serviceName + " -- " );;
            //dataMovil.append(IMEI + " -- " );;
            //dataMovil.append(IMSI + " -- "  );;
            //dataMovil.append(OPNM + " -- "  );;
            values = dataMovil.toString();
        }catch(Exception ex){
            values = ex.getMessage();
        }
        return values;
    }

}
