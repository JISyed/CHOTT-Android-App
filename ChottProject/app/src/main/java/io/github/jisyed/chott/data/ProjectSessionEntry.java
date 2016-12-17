package io.github.jisyed.chott.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;




/**
 * Created by jishenaz on 11/20/15.
 * 
 */
public class ProjectSessionEntry
{
    //
    // Statics
    //
    
    static private SimpleDateFormat dateFormatterDay;
    static private SimpleDateFormat dateFormatterTime;
    
    static private SimpleDateFormat getDayFormatter()
    {
        if(ProjectSessionEntry.dateFormatterDay == null)
        {
            ProjectSessionEntry.dateFormatterDay = new SimpleDateFormat("MMM d, ''yy", Locale.US);
            ProjectSessionEntry.dateFormatterDay.setTimeZone(TimeZone.getDefault());
        }
        return ProjectSessionEntry.dateFormatterDay;
    }
    
    static private SimpleDateFormat getTimeFormatter()
    {
        if(ProjectSessionEntry.dateFormatterTime == null)
        {
            ProjectSessionEntry.dateFormatterTime = new SimpleDateFormat("h:mm a", Locale.US);
            ProjectSessionEntry.dateFormatterDay.setTimeZone(TimeZone.getDefault());
        }
        return ProjectSessionEntry.dateFormatterTime;
    }
    
    
    
    //
    // Data
    //
    
    private String _categoryName;
    private String _projectName;
    private long _sessionLengthInMilliseconds;
    private long _startingDateTimeStamp;
    private long _endingDateTimeStamp;
    
    
    //
    // Methods
    //
    
    public ProjectSessionEntry(CategoryType newCategory, String newProjectName, SessionLengthStruct sessionDurationData, Date startTimeStamp, Date endTimeStamp)
    {
        this._categoryName = CategoryUtility.getCategoryStr(newCategory);
        this._projectName = newProjectName;
        this._sessionLengthInMilliseconds = sessionDurationData.GetWholeDurationInMilliseconds();
        this._startingDateTimeStamp = startTimeStamp.getTime();
        this._endingDateTimeStamp = endTimeStamp.getTime();
    }
    
    public ProjectSessionEntry(String newCategoryString, String newProjectName, long sessionLength, long startingUnixTime, long endingUnixTime)
    {
        this._categoryName = newCategoryString;
        this._projectName = newProjectName;
        this._sessionLengthInMilliseconds = sessionLength;
        this._startingDateTimeStamp = startingUnixTime;
        this._endingDateTimeStamp = endingUnixTime;
    }
    
    
    // toString override
    @Override
    public String toString()
    {
        return this.getFormattedDuration();
    }
    
    
    
    
    
    //
    // Getters
    //
    
    public String getCategoryName()
    {
        return this._categoryName;
    }
    
    public String getProjectName()
    {
        return this._projectName;
    }
    
    public long getSessionLengthInMilliseconds()
    {
        return this._sessionLengthInMilliseconds;
    }
    
    public long getStartingDateTimeStampAsLong()
    {
        return this._startingDateTimeStamp;
    }
    
    public long getEndingDateTimeStampAsLong()
    {
        return this._endingDateTimeStamp;
    }
    
    public SessionLengthStruct getSessionLengthData()
    {
        return new SessionLengthStruct(this._sessionLengthInMilliseconds);
    }
    
    public Date getStartingDateTimeStampAsDate()
    {
        return new Date(this._startingDateTimeStamp);
    }
    
    public Date getEndingDateTimeStampAsDate()
    {
        return new Date(this._endingDateTimeStamp);
    }
    
    public String getFormattedStartDate()
    {
        return ProjectSessionEntry.getDayFormatter().format(this.getStartingDateTimeStampAsDate());
    }
    
    public String getFormattedEndDate()
    {
        return ProjectSessionEntry.getDayFormatter().format(this.getEndingDateTimeStampAsDate());
    }
    
    public String getFormattedStartTime()
    {
        return ProjectSessionEntry.getTimeFormatter().format(this.getStartingDateTimeStampAsDate());
    }
    
    public String getFormattedEndTime()
    {
        return ProjectSessionEntry.getTimeFormatter().format(this.getEndingDateTimeStampAsDate());
    }
    
    
    public String getFormattedDateRange()
    {
        return this.getFormattedStartDate() + " to " + this.getFormattedEndDate();
    }
    
    public String getFormattedTimeRange()
    {
        return this.getFormattedStartTime() + " - " + this.getFormattedEndTime();
    }
    
    public String getFormattedDuration()
    {
        String formattedDuration;
        
        SessionLengthStruct durationData = this.getSessionLengthData();
        int hour = durationData.GetHoursPortionOfDuration();
        if(hour != 0)
        {
            int rolledOverMinutes = durationData.GetMinutesPortionOfDuration();
            formattedDuration = Integer.toString(hour) + " hr, " + Integer.toString(rolledOverMinutes) + " min";
        }
        else
        {
            int totalMinutes = durationData.GetWholeDurationInMinutes();
            formattedDuration = Integer.toString(totalMinutes) + " min";
        }
        
        return formattedDuration;
    }
}
