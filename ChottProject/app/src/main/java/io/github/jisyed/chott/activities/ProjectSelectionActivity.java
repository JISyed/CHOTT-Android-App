package io.github.jisyed.chott.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import io.github.jisyed.chott.R;
import io.github.jisyed.chott.data.ProjectListingStruct;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;
import io.github.jisyed.chott.data.utility.ChottJsonUtility;
import io.github.jisyed.chott.session.ChottSessionSingleton;



public class ProjectSelectionActivity extends AppCompatActivity
{
    // For intents
    public static final String EXTRA_PROJECT = "Chott_Project";
    public static final String EXTRA_ADD = "Chott_Add";
    
    // For intent results
    public static final int REQUEST_ADD_PROJECT = 8080;
    
    
    private ListView theListView;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);
        
        // Setup the ListView
        this.theListView = (ListView) findViewById(R.id.listViewProjects);
    
        CategoryType currentCategory = ChottSessionSingleton.I().getCurrentCategory();
        LinkedList<ProjectListingStruct> currentCategoryListing = this.retrieveProjectListOfCertainCategory(currentCategory);
        
        // Custom adapter 
        theListView.setAdapter(new ArrayAdapter<ProjectListingStruct>(this, R.layout.listitem_single_project, R.id.tvSingleProjectName, currentCategoryListing) 
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View currRow = super.getView(position, convertView, parent);
                   
                // Setup additional view form listitem_single_project, if any...
                
                /////////////////////////////////////////////
                // Setup the HISTORY button in *each* listing
                /////////////////////////////////////////////
                ImageButton historyButton = (ImageButton) currRow.findViewById(R.id.ibOneProjectToHistory);
                ProjectListingStruct currProject = (ProjectListingStruct) theListView.getItemAtPosition(position);
                historyButton.setTag(currProject.getProjectName());
                historyButton.setOnClickListener(new View.OnClickListener() 
                {
                    @Override
                    public void onClick(View v)
                    {
                        String projectNameReferredByButton = (String) v.getTag();
                        Intent historyIntent = new Intent(ProjectSelectionActivity.this, ProjectHistoryActivity.class);
                        historyIntent.putExtra(EXTRA_PROJECT, projectNameReferredByButton);
                        startActivity(historyIntent);
                    }
                });
                
                return currRow;
            }
        });
        
        // Setup event for when an item listing is clicked on (not the buttons on the listing)
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
            ////////////////////////////////////////
            // Start the TIMER for the given project
            ////////////////////////////////////////
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ProjectListingStruct currentProject = (ProjectListingStruct) theListView.getItemAtPosition(position);
                startTimerActivity(currentProject);
            }
        });
        
        
        
        // Setup the colors of the Project Selection screen
        int regularColorId = CategoryUtility.getRegularColorId(currentCategory);
        int darkColorId = CategoryUtility.getDarkColorId(currentCategory);
        final String currCategoryName = CategoryUtility.getCategoryStr(currentCategory);
        int iconId = CategoryUtility.getIconId(currentCategory);
        
        LinearLayout llProjectHeader = (LinearLayout) findViewById(R.id.llProjectTopHeader);
        llProjectHeader.setBackgroundColor(getResources().getColor(regularColorId));
    
        ImageView ivCurrCategoryIco = (ImageView) findViewById(R.id.ivProjectCurrProjectIcon);
        ivCurrCategoryIco.setImageResource(iconId);
    
        TextView tvCurrCategoryName = (TextView) findViewById(R.id.tvProjectCurrProjectName);
        tvCurrCategoryName.setText(currCategoryName);
        
        ImageButton ibAddButton = (ImageButton) findViewById(R.id.ibProjectAddProject);
        ibAddButton.setBackgroundColor(getResources().getColor(darkColorId));
        
        
        
        // Add click event for add button
        ibAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /////////////////////////////////
                // Proceed to ADD a new project
                /////////////////////////////////
                
                //Toast.makeText(ProjectSelectionActivity.this, "Add Project to " + currCategoryName, Toast.LENGTH_SHORT).show();
                Intent addProjectIntent = new Intent(ProjectSelectionActivity.this, AddProjectActivity.class);
                startActivityForResult(addProjectIntent, REQUEST_ADD_PROJECT);
            }
        });
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD_PROJECT)
        {
            if(resultCode == AddProjectActivity.RESULT_ADD)
            {
                String newProjectName = data.getStringExtra(EXTRA_ADD);
                //Toast.makeText(ProjectSelectionActivity.this, "Add: " + newProjectName, Toast.LENGTH_SHORT).show();
                this.addNewProject(newProjectName);
            }
            else if(resultCode == AddProjectActivity.RESULT_ADD_AND_TRACK)
            {
                String newProjectName = data.getStringExtra(EXTRA_ADD);
                //Toast.makeText(ProjectSelectionActivity.this, "Add & Track: " + newProjectName, Toast.LENGTH_SHORT).show();
                ProjectListingStruct newlyCreatedProject = this.addNewProject(newProjectName);
                startTimerActivity(newlyCreatedProject);
            }
            else if(resultCode == AddProjectActivity.RESULT_CANCEL)
            {
                Toast.makeText(ProjectSelectionActivity.this, "Cancelled Adding Project", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    
    
    
    private ProjectListingStruct addNewProject(String newProjectName)
    {
        CategoryType currentCategory = ChottSessionSingleton.I().getCurrentCategory();
        ProjectListingStruct newProject = new ProjectListingStruct(currentCategory, newProjectName);
    
        // Get the appropriate list
        LinkedList<ProjectListingStruct> currentCategoryListing = this.retrieveProjectListOfCertainCategory(currentCategory);
        
        // Add the new project to the appropriate list
        currentCategoryListing.add(newProject);
        this.theListView.invalidateViews();
    
        ChottJsonUtility.saveProjectsList(this, 
                                          ChottJsonUtility.getProjectFileNameByCategory(currentCategory),
                                          currentCategoryListing
        );
        
        return newProject;
    }
    
    
    private void startTimerActivity(ProjectListingStruct projectToTime)
    {
        Intent sessionTimerIntent = new Intent(ProjectSelectionActivity.this, ProjectSessionTimerActivity.class);
        sessionTimerIntent.putExtra(EXTRA_PROJECT, projectToTime.getProjectName());
        startActivity(sessionTimerIntent);
    }
    
    
    
    private LinkedList<ProjectListingStruct> retrieveProjectListOfCertainCategory(CategoryType category)
    {
        LinkedList<ProjectListingStruct> currentCategoryListing = null;
        switch (category)
        {
            case Art:
                currentCategoryListing = ChottSessionSingleton.I().getProjectsListArt();
                break;
            case Code:
                currentCategoryListing = ChottSessionSingleton.I().getProjectsListCode();
                break;
            case Music:
                currentCategoryListing = ChottSessionSingleton.I().getProjectsListMusic();
                break;
            case Writing:
                currentCategoryListing = ChottSessionSingleton.I().getProjectsListWriting();
                break;
        }
        
        return currentCategoryListing;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_selection, menu);
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
