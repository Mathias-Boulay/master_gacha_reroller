package fr.spse.master_gacha_reroller.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {

    /**
     * Turn a drawable into a bitmap
     * @param drawable The drawable to extract the bitmap from
     * @param downscaleFactor The 1/N divider ration to downsample the image
     * @return A bitmap extracted from the drawable
     */
    public static Bitmap drawableToBitmap(Drawable drawable, int downscaleFactor) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // We ask for the bounds if they have been set as they would be most
        // correct, then we check we are  > 0
        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        // Now we check we are > 0
        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width/ downscaleFactor, height <= 0 ? 1 : height/ downscaleFactor,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        drawable = null;

        return bitmap;
    }
}
