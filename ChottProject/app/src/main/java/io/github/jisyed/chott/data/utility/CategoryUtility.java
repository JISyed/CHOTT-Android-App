package io.github.jisyed.chott.data.utility;

import io.github.jisyed.chott.R;

/**
 * Created by jishenaz on 11/8/15.
 * 
 */
public final class CategoryUtility
{
    // No constructor
    private CategoryUtility() {}
    
    public static String getCategoryStr(CategoryType type)
    {
        switch (type)
        {
            case Art:
                return "Art";
            case Code:
                return "Code";
            case Music:
                return "Music";
            case Writing:
                return "Writing";
        }
        
        return "Undefined Category";
    }
    
    public static int getRegularColorId(CategoryType type)
    {
        int colorId = 0;
        
        switch (type)
        {
            case Art:
                colorId = R.color.colorChottArtRegular;
                break;
            case Code:
                colorId = R.color.colorChottCodeRegular;
                break;
            case Music:
                colorId = R.color.colorChottMusicRegular;
                break;
            case Writing:
                colorId = R.color.colorChottWritingRegular;
                break;
        }
        
        return colorId;
    }
    
    public static int getDarkColorId(CategoryType type)
    {
        int colorId = 0;
        
        switch (type)
        {
            case Art:
                colorId = R.color.colorChottArtDark;
                break;
            case Code:
                colorId = R.color.colorChottCodeDark;
                break;
            case Music:
                colorId = R.color.colorChottMusicDark;
                break;
            case Writing:
                colorId = R.color.colorChottWritingDark;
                break;
        }
        
        return colorId;
    }
    
    public static int getLiteColorId(CategoryType type)
    {
        int colorId = 0;
        
        switch (type)
        {
            case Art:
                colorId = R.color.colorChottArtLite;
                break;
            case Code:
                colorId = R.color.colorChottCodeLite;
                break;
            case Music:
                colorId = R.color.colorChottMusicLite;
                break;
            case Writing:
                colorId = R.color.colorChottWritingLite;
                break;
        }
        
        return colorId;
    }
    
    public static int getIconId(CategoryType type)
    {
        int iconId = 0;
        
        switch (type)
        {
            case Art:
                iconId = R.drawable.icon_art;
                break;
            case Code:
                iconId = R.drawable.icon_code;
                break;
            case Music:
                iconId = R.drawable.icon_music;
                break;
            case Writing:
                iconId = R.drawable.icon_digital_writing;
                break;
        }
        
        return iconId;
    }
}
