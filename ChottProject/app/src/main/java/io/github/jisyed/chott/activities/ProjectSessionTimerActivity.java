package io.github.jisyed.chott.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.Date;
import java.util.LinkedList;

import io.github.jisyed.chott.R;
import io.github.jisyed.chott.data.ProjectSessionEntry;
import io.github.jisyed.chott.data.SessionLengthStruct;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;
import io.github.jisyed.chott.data.utility.ChottJsonUtility;
import io.github.jisyed.chott.session.ChottSessionSingleton;
import io.github.jisyed.chott.session.utility.BitmapLoadUtility;



public class ProjectSessionTimerActivity extends AppCompatActivity
{
    private final long TIMER_UPDATE_INTERVAL_MS = 500; // In ms
    
    private TextView timerDisplay;
    private long startTime;
    private SessionLengthStruct sessionLengthData = null;
    private Date sessionStartStamp;
    private Date sessionEndStamp;
    private String currentProjectName;
    
    
    Handler timerHandler = new Handler();
    
    // Timer update routine
    Runnable timerRunnable = new Runnable() 
    {
        @Override
        public void run() 
        {
            // Update the whole duration into the session data struct
            long currTimeSinceStart = (System.currentTimeMillis() - startTime);
            sessionLengthData.SetWholeDurationInMilliseconds(currTimeSinceStart);
            timerDisplay.setText(sessionLengthData.toString());
            
            // Recall this runnable soon
            timerHandler.postDelayed(this, TIMER_UPDATE_INTERVAL_MS);
        }
    };
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_session_timer);
        
        // Setup the colors based on category
        CategoryType currCategory = ChottSessionSingleton.I().getCurrentCategory();
        int colorDarkId = CategoryUtility.getDarkColorId(currCategory);
        int colorRegularId = CategoryUtility.getRegularColorId(currCategory);
        int colorLiteId = CategoryUtility.getLiteColorId(currCategory);
        int categoryIconId = CategoryUtility.getIconId(currCategory);
        String categoryStr = CategoryUtility.getCategoryStr(currCategory);
        
        int colorDark = getResources().getColor(colorDarkId);
        int colorRegular = getResources().getColor(colorRegularId);
        int colorLite = getResources().getColor(colorLiteId);
        
        ImageView categoryIcon = (ImageView) findViewById(R.id.ivTimerCategoryIcon);
        categoryIcon.setImageResource(categoryIconId);
        categoryIcon.setColorFilter(colorLite);
    
        TextView categoryLabel = (TextView) findViewById(R.id.tvTimerCategoryName);
        categoryLabel.setText(categoryStr);
        categoryLabel.setTextColor(colorLite);
        
        TextView projectLabel = (TextView) findViewById(R.id.tvTimerProjectName);
        Intent inboundIntent = getIntent();
        this.currentProjectName = null;
        this.currentProjectName = inboundIntent.getStringExtra(ProjectSelectionActivity.EXTRA_PROJECT);
        projectLabel.setText(currentProjectName);
        projectLabel.setTextColor(colorLite);
        
        
        ImageView timerRing = (ImageView) findViewById(R.id.timerRing);
        timerRing.setImageBitmap(
                BitmapLoadUtility.decodeSampledBitmapFromResource(
                        getResources(), 
                        R.drawable.timer_ring_img, 
                        256, 
                        256
                )
        );
        timerRing.setColorFilter(colorRegular);
        
        
        // Setup references
        this.timerDisplay = (TextView) findViewById(R.id.tvTimerCurrentTime);
        this.timerDisplay.setTextColor(colorLite);
        
        
        
        
        // Setup the colors and events for buttons
        Button buttonFinish = (Button) findViewById(R.id.btTimerFinish);
        Button buttonCancel = (Button) findViewById(R.id.btTimerCancel);
        Button buttonRestart = (Button) findViewById(R.id.btTimerRestart);
        buttonFinish.setBackgroundColor(colorDark);
        buttonFinish.setTextColor(colorLite);
        
        buttonFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CategoryType currentCategory = ChottSessionSingleton.I().getCurrentCategory();
                ProjectSessionTimerActivity.this.sessionEndStamp = new Date();
                ProjectSessionEntry sessionEntry = new ProjectSessionEntry(currentCategory,
                                                                           ProjectSessionTimerActivity.this.currentProjectName,
                                                                           ProjectSessionTimerActivity.this.sessionLengthData,
                                                                           ProjectSessionTimerActivity.this.sessionStartStamp,
                                                                           ProjectSessionTimerActivity.this.sessionEndStamp
                );
                
                ChottSessionSingleton.I().addNewEntry(sessionEntry);
                LinkedList<ProjectSessionEntry> listToSave = ChottSessionSingleton.I().getEntryListOfCurrentCategory();
                ChottJsonUtility.saveEntriesList(ProjectSessionTimerActivity.this,
                                                 ChottJsonUtility.getEntryFileNameByCategory(currentCategory),
                                                 listToSave
                );
                
                Toast.makeText(
                        ProjectSessionTimerActivity.this,
                        "Session recorded for " + ProjectSessionTimerActivity.this.currentProjectName,
                        Toast.LENGTH_LONG
                ).show();
                
                finish();
            }
        });
        
        
        buttonRestart.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ProjectSessionTimerActivity.this, "Restarted", Toast.LENGTH_SHORT).show();
                stopTimer();
                restartTimerValue();
                startTimer();
            }
        });
        
        
        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ProjectSessionTimerActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    
    
        if(this.sessionLengthData == null)
        {
            this.sessionLengthData = new SessionLengthStruct();
        }
        this.restartTimerValue();
        
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        
        // Stop the timer
        this.stopTimer();
    }
    
    
    @Override
    protected void onStart()
    {
        super.onStart();
        
        // Start Timer
        this.startTimer();
    }
    
    @Override
    protected void onDestroy()
    {
        this.restartTimerValue();
        this.sessionLengthData = null;
        this.sessionStartStamp = null;
        this.sessionEndStamp = null;
        super.onDestroy();
    }
    
    
    @Override
    public void onBackPressed()
    {
        Toast.makeText(ProjectSessionTimerActivity.this, "Press \"Cancel\" to go back", Toast.LENGTH_SHORT).show();
    }
    
    
    
    
    
    
    
    private void stopTimer()
    {
        timerHandler.removeCallbacks(timerRunnable);
    }
    
    private void restartTimerValue()
    {
        this.startTime = System.currentTimeMillis();
        this.sessionLengthData.SetWholeDurationInSeconds(0);
    }
    
    private void startTimer()
    {
        // Mark the starting timestamp
        if(this.sessionStartStamp == null)
        {
            this.sessionStartStamp = new Date();
        }
            
        // Run a handler to start the timer
        timerHandler.postDelayed(timerRunnable, 0);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_session_timer, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }*/
        
        return super.onOptionsItemSelected(item);
    }
    
}
