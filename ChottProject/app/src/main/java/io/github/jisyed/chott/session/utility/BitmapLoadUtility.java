package io.github.jisyed.chott.session.utility;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

/**
 * Created by jishenaz on 11/9/15.
 * 
 * Credit to Android documentation
 * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
 */
public final class BitmapLoadUtility
{
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) 
        {
            
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight 
                && (halfWidth  / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        
        return inSampleSize;
    }
    
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) 
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
