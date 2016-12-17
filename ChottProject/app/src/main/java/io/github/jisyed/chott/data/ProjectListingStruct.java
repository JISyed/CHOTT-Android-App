package io.github.jisyed.chott.data;

import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.CategoryUtility;



/**
 * Created by jishenaz on 11/8/15.
 * 
 */
public class ProjectListingStruct
{
    private String _categoryName;
    private String _projectName;
    
    // Constructor
    public ProjectListingStruct(String categoryName, String projectName)
    {
        this._categoryName = categoryName;
        this._projectName = projectName;
    }
    
    // Constructor
    public ProjectListingStruct(CategoryType type, String projectName)
    {
        this._categoryName = CategoryUtility.getCategoryStr(type);
        this._projectName = projectName;
    }
    
    
    // toString override
    @Override
    public String toString()
    {
        return this._projectName;
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
    
}
