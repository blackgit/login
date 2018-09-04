package com.example.desarr.seguridad.custom;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class InfoControl extends Application{

    public static String getInfo(Context context, Activity activity){
        StringBuffer dataMovil = new StringBuffer();
        Context aContext = context;

        String myDeviceModel = android.os.Build.MODEL;
        String serviceName = context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
        String IMEI,IMSI,OPNM;

        try {
            int permissionCheck = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_CALENDAR);
            IMEI = m_telephonyManager.getDeviceId();
            IMSI = m_telephonyManager.getSubscriberId();
            OPNM = m_telephonyManager.getNetworkOperatorName();

            dataMovil.append(myDeviceModel + " -- ");
            dataMovil.append(serviceName + " -- " );;
            dataMovil.append(IMEI + " -- " );;
            dataMovil.append(IMSI + " -- "  );;
            dataMovil.append(OPNM + " -- "  );;
        }catch(Exception ex){

        }
        return dataMovil.toString();
    }

}
