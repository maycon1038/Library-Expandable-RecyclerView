package com.android.msm.recycleviewexpandable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;


public class Util {


    public static void Tag(Context ctx, String m) {

        Log.d(ctx.getClass().getSimpleName(), m);

    }


    public static double distanceBetweendouble(double lat, double lng, double lat2, double lng2) {
        Location loc1 = new Location(LocationManager.GPS_PROVIDER);
        Location loc2 = new Location(LocationManager.GPS_PROVIDER);
        loc1.setLatitude(lat);
        loc1.setLongitude(lng);
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);


        return loc1.distanceTo(loc2);
    }

    public static String distEntrePontos(int valor) {

        String km;
        String m;
        String rt = null;

        if (valor >= 1000) {

            km = String.valueOf(valor).substring(0, String.valueOf(valor).length() - 3) + " Km";

            m = String.valueOf(valor).substring(String.valueOf(valor).length() - 3);
            if (!m.isEmpty()) {
                m += " m";
            }
            rt = km + " " + m;
        } else if (valor < 1000) {

            m = String.valueOf(valor) + " m";

            rt = m;
        }

        return rt;
    }


    //convert Text from bitmap
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

}
