package io.github.jisyed.chott.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.jisyed.chott.R;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;
import io.github.jisyed.chott.session.ChottSessionSingleton;




public class AddProjectActivity extends AppCompatActivity
{
    public static final int RESULT_ADD = 9000;
    public static final int RESULT_ADD_AND_TRACK = 9001;
    public static final int RESULT_CANCEL = 9002;
    
    private EditText inputForProjectName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
    
        // Setup the colors of the Project Selection screen
        CategoryType currentCategory = ChottSessionSingleton.I().getCurrentCategory();
        int regularColorId = CategoryUtility.getRegularColorId(currentCategory);
        int darkColorId = CategoryUtility.getDarkColorId(currentCategory);
        final String currCategoryName = CategoryUtility.getCategoryStr(currentCategory);
        int iconId = CategoryUtility.getIconId(currentCategory);
    
        LinearLayout llHeader = (LinearLayout) findViewById(R.id.llAdderTopHeader);
        llHeader.setBackgroundColor(getResources().getColor(regularColorId));
    
        ImageView ivCurrCategoryIco = (ImageView) findViewById(R.id.ivAdderCategoryIcon);
        ivCurrCategoryIco.setImageResource(iconId);
    
        TextView tvCurrCategoryName = (TextView) findViewById(R.id.tvAdderCategoryName);
        tvCurrCategoryName.setText(currCategoryName);
        
        
        // Setup references
        this.inputForProjectName = (EditText) findViewById(R.id.etAdderProjectName);
        
        
        // Setup buttons
        Button btAddAndTrack = (Button) findViewById(R.id.btAdderAddAndTrack);
        Button btAdd = (Button) findViewById(R.id.btAdderAdd);
        Button btCancel = (Button) findViewById(R.id.btAdderCancel);
        
        btAddAndTrack.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                String newProjectName = inputForProjectName.getText().toString();
                if(newProjectName.matches(""))
                {
                    Toast.makeText(AddProjectActivity.this, "Enter a Project Name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent resultIntention = new Intent();
                    resultIntention.putExtra(ProjectSelectionActivity.EXTRA_ADD, newProjectName);
                    AddProjectActivity.this.setResult(RESULT_ADD_AND_TRACK, resultIntention);
                    finish();
                }
            }
        });
        
        btAdd.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                String newProjectName = inputForProjectName.getText().toString();
                if(newProjectName.matches(""))
                {
                    Toast.makeText(AddProjectActivity.this, "Enter a Project Name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent resultIntention = new Intent();
                    resultIntention.putExtra(ProjectSelectionActivity.EXTRA_ADD, newProjectName);
                    AddProjectActivity.this.setResult(RESULT_ADD, resultIntention);
                    finish();
                }
            }
        });
        
        btCancel.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                AddProjectActivity.this.setResult(RESULT_CANCEL);
                finish();
            }
        });
        
    }
    
    
    @Override
    public void onBackPressed()
    {
        AddProjectActivity.this.setResult(RESULT_CANCEL);
        super.onBackPressed();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_project, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       /* // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }*/
        
        return super.onOptionsItemSelected(item);
    }
}
