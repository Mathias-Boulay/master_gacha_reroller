package fr.spse.master_gacha_reroller.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Tools {


    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPx(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

}
