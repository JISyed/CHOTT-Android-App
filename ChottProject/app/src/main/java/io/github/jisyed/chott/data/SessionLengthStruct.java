package io.github.jisyed.chott.data;

/**
 * Created by jishenaz on 11/20/15.
 * 
 */
public class SessionLengthStruct
{
    //
    // Data
    // 
    
    private long _durationInMilliseconds;
    
    
    
    //
    // Methods
    //
    
    // Constructor
    public SessionLengthStruct()
    {
        this._durationInMilliseconds = 0;
    }
    
    // Specialized constructor
    public SessionLengthStruct(long newStartingDuration)
    {
        this._durationInMilliseconds = newStartingDuration;
    }
    
    
    // toString Override
    @Override
    public String toString()
    {
        int s = this.GetSecondsPortionOfDuration();
        int m = this.GetMinutesPortionOfDuration();
        int h = this.GetHoursPortionOfDuration();
        
        String finalFormat;
        
        if(h == 0)
        {
            finalFormat = String.format("%d:%02d", m, s);
        }
        else
        {
            finalFormat = String.format("%d:%02d:%02d", h, m, s);
        }
        
        return finalFormat;
    }
    
    
    
    //
    // Getters
    //
    
    public int GetWholeDurationInSeconds()
    {
        return (int) this._durationInMilliseconds/1000;
    }
    
    public long GetWholeDurationInMilliseconds()
    {
        return this._durationInMilliseconds;
    }
    
    public int GetWholeDurationInMinutes()
    {
        return this.GetWholeDurationInSeconds() / 60;
    }
    
    // Does not return the whole duration!
    public int GetSecondsPortionOfDuration()
    {
        return this.GetWholeDurationInSeconds() % 60;
    }
    
    // Does not return the whole duration!
    public int GetMinutesPortionOfDuration()
    {
        int minutes = this.GetWholeDurationInSeconds() / 60;
        minutes = minutes % 60;
        return minutes;
    }
    
    public int GetHoursPortionOfDuration()
    {
        int minutes = this.GetWholeDurationInSeconds() / 60;
        return minutes / 60;
    }
    
    
    //
    // Setters
    //
    
    
    public void SetWholeDurationInSeconds(int newWholeDuration)
    {
        this._durationInMilliseconds = newWholeDuration * 1000;
    }
    
    public void SetWholeDurationInMilliseconds(long newWholeDuration)
    {
        this._durationInMilliseconds = newWholeDuration;
    }
    
}
