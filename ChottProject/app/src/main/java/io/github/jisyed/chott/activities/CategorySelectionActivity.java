package io.github.jisyed.chott.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import io.github.jisyed.chott.R;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.session.ChottSessionSingleton;




public class CategorySelectionActivity extends AppCompatActivity
{
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
    
        ChottSessionSingleton.setCurrentContext(this);
        
        Button btCode = (Button) this.findViewById(R.id.buttonCategoryCode);
        Button btArt = (Button) this.findViewById(R.id.buttonCategoryArt);
        Button btMusic = (Button) this.findViewById(R.id.buttonCategoryMusic);
        Button btWriting = (Button) this.findViewById(R.id.buttonCategoryWriting);
        
        btCode.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                ChottSessionSingleton.I().setCurrentCategory(CategoryType.Code);
                Intent newIntent = new Intent(CategorySelectionActivity.this, ProjectSelectionActivity.class);
                startActivity(newIntent);
            }
        });
        
        btArt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChottSessionSingleton.I().setCurrentCategory(CategoryType.Art);
                Intent newIntent = new Intent(CategorySelectionActivity.this, ProjectSelectionActivity.class);
                startActivity(newIntent);
            }
        });
        
        btMusic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChottSessionSingleton.I().setCurrentCategory(CategoryType.Music);
                Intent newIntent = new Intent(CategorySelectionActivity.this, ProjectSelectionActivity.class);
                startActivity(newIntent);
            }
        });
        btWriting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChottSessionSingleton.I().setCurrentCategory(CategoryType.Writing);
                Intent newIntent = new Intent(CategorySelectionActivity.this, ProjectSelectionActivity.class);
                startActivity(newIntent);
            }
        });
        
    }
    
    @Override
    protected void onDestroy()
    {
        ChottSessionSingleton.setCurrentContext(null);
        ChottSessionSingleton.I().dereferenceListsForGarbageCollector();
        super.onDestroy();
    }
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_selection, menu);
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
