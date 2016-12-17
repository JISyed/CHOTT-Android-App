package io.github.jisyed.chott.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import io.github.jisyed.chott.R;
import io.github.jisyed.chott.data.ProjectSessionEntry;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;
import io.github.jisyed.chott.session.ChottSessionSingleton;




public class ProjectHistoryActivity extends AppCompatActivity
{

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_history);
    
        Intent inboundIntent = getIntent();
        final String projectName = inboundIntent.getStringExtra(ProjectSelectionActivity.EXTRA_PROJECT);
        
        // Setup the ListView
        final ListView theListView = (ListView) findViewById(R.id.listViewHistory);
    
        // Make a list of narrowed down entries based off projec name
        final LinkedList<ProjectSessionEntry> entriesOfCurrentCategory = ChottSessionSingleton.I().getEntryListOfCurrentCategory();
        LinkedList<ProjectSessionEntry> entriesForCurrentProject = new LinkedList<>();
        for(ProjectSessionEntry entry : entriesOfCurrentCategory)
        {
            if(projectName.equals(entry.getProjectName()))
            {
                entriesForCurrentProject.add(entry);
            }
        }
        
        // Custom adapter 
        theListView.setAdapter(new ArrayAdapter<ProjectSessionEntry>(this, R.layout.listitem_project_session_entry, R.id.tvProjectEntryDuration, entriesForCurrentProject) 
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View currRow = super.getView(position, convertView, parent);
            
                // Setup additional view form listitem_project_session_entry, if any...
                
                TextView tvDate = (TextView) currRow.findViewById(R.id.tvProjectEntryDate);
                TextView tvTime = (TextView) currRow.findViewById(R.id.tvProjectEntryTime);
    
                ProjectSessionEntry currentEntry = (ProjectSessionEntry) theListView.getItemAtPosition(position);
                
                tvDate.setText(currentEntry.getFormattedStartDate());
                tvTime.setText(currentEntry.getFormattedTimeRange());
                
                return currRow;
            }
        });
    
        // Setup the colors of the Project Selection screen
        CategoryType currentCategory = ChottSessionSingleton.I().getCurrentCategory();
        int regularColorId = CategoryUtility.getRegularColorId(currentCategory);
        int darkColorId = CategoryUtility.getDarkColorId(currentCategory);
        final String currCategoryName = CategoryUtility.getCategoryStr(currentCategory);
        int iconId = CategoryUtility.getIconId(currentCategory);
    
        LinearLayout llProjectHeader = (LinearLayout) findViewById(R.id.llHistoryTopHeader);
        llProjectHeader.setBackgroundColor(getResources().getColor(regularColorId));
    
        ImageView ivCurrCategoryIco = (ImageView) findViewById(R.id.ivHistoryCategoryIcon);
        ivCurrCategoryIco.setImageResource(iconId);
    
        TextView tvCurrCategoryName = (TextView) findViewById(R.id.tvHistoryCategoryName);
        tvCurrCategoryName.setText(currCategoryName);
        
        // Setup project name
        TextView tvCurrProjectName = (TextView) findViewById(R.id.tvHistoryProjectName);
        tvCurrProjectName.setText(projectName);
    }
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_history, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }*/
        
        return super.onOptionsItemSelected(item);
    }
}
