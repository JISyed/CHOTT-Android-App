package io.github.jisyed.chott.data.utility;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import io.github.jisyed.chott.data.ProjectListingStruct;
import io.github.jisyed.chott.data.ProjectSessionEntry;


/**
 * Created by jishenaz on 11/21/15.
 * 
 */
public class ChottJsonUtility
{
    private static final String FILENAME_PROJECTS_ART = "chott_projects_art";
    private static final String FILENAME_PROJECTS_CODE = "chott_projects_code";
    private static final String FILENAME_PROJECTS_MUSIC = "chott_projects_music";
    private static final String FILENAME_PROJECTS_WRITING = "chott_projects_writing";
    
    private static final String FILENAME_ENTRIES_ART = "chott_entries_art";
    private static final String FILENAME_ENTRIES_CODE = "chott_entries_code";
    private static final String FILENAME_ENTRIES_MUSIC = "chott_entries_music";
    private static final String FILENAME_ENTRIES_WRITING = "chott_entries_writing";
    
    
    
    private static final String ENCODING_JSON = "UTF-8";
    
    
    // DO NOT CHANGE, will mess up serialization!
    private static final String JSON_KEY_CATEGORY = "category";
    private static final String JSON_KEY_PROJECT = "project";
    private static final String JSON_KEY_DURATION = "duration";
    private static final String JSON_KEY_START = "start";
    private static final String JSON_KEY_END = "end";
    
    
    
    // No constructor
    private ChottJsonUtility(){}
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // For ProjectSessionEntry
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    
    
    static public String getEntryFileNameByCategory(CategoryType category)
    {
        switch (category)
        {
            case Art:
                return FILENAME_ENTRIES_ART;
            case Code:
                return FILENAME_ENTRIES_CODE;
            case Music:
                return FILENAME_ENTRIES_MUSIC;
            case Writing:
                return FILENAME_ENTRIES_WRITING;
        }
        
        return "";
    }
    
    
    
    //
    // Serialization
    //
    
    static public void saveEntriesList(Context activity, String fileName, LinkedList<ProjectSessionEntry> entries)
    {
        FileOutputStream fos;
        try
        {
            fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            saveJsonArrayOfEntries(fos, entries);
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    static private void saveJsonArrayOfEntries(OutputStream out, LinkedList<ProjectSessionEntry> entries) throws IOException
    {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, ENCODING_JSON));
        writer.setIndent("   ");
        writeEntryArray(writer, entries);
        writer.close();
    }
    
    static private void writeEntryArray(JsonWriter writer, LinkedList<ProjectSessionEntry> entries) throws IOException 
    {
        writer.beginArray();
        for (ProjectSessionEntry entry : entries) 
        {
            writeEntry(writer, entry);
        }
        writer.endArray();
    }
    
    static private void writeEntry(JsonWriter writer, ProjectSessionEntry entry) throws IOException 
    {
        writer.beginObject();
        
        writer.name(JSON_KEY_CATEGORY).value(entry.getCategoryName());
        writer.name(JSON_KEY_PROJECT).value(entry.getProjectName());
        writer.name(JSON_KEY_DURATION).value(entry.getSessionLengthInMilliseconds());
        writer.name(JSON_KEY_START).value(entry.getStartingDateTimeStampAsLong());
        writer.name(JSON_KEY_END).value(entry.getEndingDateTimeStampAsLong());
        
        writer.endObject();
    }
    
    
    
    
    
    //
    // Deserialization
    //
    
    
    static public LinkedList<ProjectSessionEntry> loadListOfEntries(Context activity, String fileName)
    {
        LinkedList<ProjectSessionEntry> loadedEntries = null;
        FileInputStream fis;
        try
        {
            fis = activity.openFileInput(fileName);
            loadedEntries = loadJsonArrayOfEntries(fis);
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            // Create a new empty list if the file doesn't exist
            loadedEntries = new LinkedList<>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return loadedEntries;
    }
    
    static private LinkedList<ProjectSessionEntry> loadJsonArrayOfEntries(InputStream in) throws IOException
    {
        LinkedList<ProjectSessionEntry> loadedList = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in, ENCODING_JSON));
        try
        {
            loadedList = readEntriesArray(reader);
            
        }
        finally
        {
            reader.close();
        }
        
        return loadedList;
    }
    
    static private LinkedList<ProjectSessionEntry> readEntriesArray(JsonReader reader) throws IOException 
    {
        LinkedList<ProjectSessionEntry> entries = new LinkedList<>();
        
        reader.beginArray();
        while (reader.hasNext()) 
        {
            entries.add(readEntry(reader));
        }
        reader.endArray();
        return entries;
    }
    
    static private ProjectSessionEntry readEntry(JsonReader reader) throws IOException 
    {
        String categoryName = null;
        String projectName = null;
        long duration = 0;
        long start = 0;
        long end = 0;
        
        reader.beginObject();
        while (reader.hasNext()) 
        {
            String key = reader.nextName();
            
            if(key.equals(JSON_KEY_CATEGORY))
            {
                categoryName = reader.nextString();
            }
            else if(key.equals(JSON_KEY_PROJECT))
            {
                projectName = reader.nextString();
            }
            else if(key.equals(JSON_KEY_DURATION))
            {
                duration = reader.nextLong();
            }
            else if(key.equals(JSON_KEY_START))
            {
                start = reader.nextLong();
            }
            else if(key.equals(JSON_KEY_END))
            {
                end = reader.nextLong();
            }
            else 
            {
                reader.skipValue();
            }
        }
        reader.endObject();
        
        if(duration != end - start)
        {
            duration = end - start;
        }
        
        return new ProjectSessionEntry(categoryName, projectName, duration, start, end);
    }
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // For ProjectListingStruct
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    
    static public String getProjectFileNameByCategory(CategoryType category)
    {
        switch (category)
        {
            case Art:
                return FILENAME_PROJECTS_ART;
            case Code:
                return FILENAME_PROJECTS_CODE;
            case Music:
                return FILENAME_PROJECTS_MUSIC;
            case Writing:
                return FILENAME_PROJECTS_WRITING;
        }
        
        return "";
    }
    
    
    //
    // Serialization
    //
    
    
    static public void saveProjectsList(Context activity, String fileName, LinkedList<ProjectListingStruct> projects)
    {
        FileOutputStream fos;
        try
        {
            fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            saveJsonArrayOfProjects(fos, projects);
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    static private void saveJsonArrayOfProjects(OutputStream out, LinkedList<ProjectListingStruct> projects) throws IOException
    {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, ENCODING_JSON));
        writer.setIndent("   ");
        writeProjectArray(writer, projects);
        writer.close();
    }
    
    static private void writeProjectArray(JsonWriter writer, LinkedList<ProjectListingStruct> projects) throws IOException
    {
        writer.beginArray();
        for (ProjectListingStruct project : projects)
        {
            writeProject(writer, project);
        }
        writer.endArray();
    }
    
    static private void writeProject(JsonWriter writer, ProjectListingStruct project) throws IOException
    {
        writer.beginObject();
        
        writer.name(JSON_KEY_CATEGORY).value(project.getCategoryName());
        writer.name(JSON_KEY_PROJECT).value(project.getProjectName());
        
        writer.endObject();
    }
    
    
    
    //
    // Deserialization
    //
    
    
    static public LinkedList<ProjectListingStruct> loadListOfProjects(Context activity, String fileName)
    {
        LinkedList<ProjectListingStruct> loadedEntries = null;
        FileInputStream fis;
        try
        {
            fis = activity.openFileInput(fileName);
            loadedEntries = loadJsonArrayOfProjects(fis);
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            // Create a new empty list if the file doesn't exist
            loadedEntries = new LinkedList<>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return loadedEntries;
    }
    
    static private LinkedList<ProjectListingStruct> loadJsonArrayOfProjects(InputStream in) throws IOException
    {
        LinkedList<ProjectListingStruct> loadedList = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in, ENCODING_JSON));
        try
        {
            loadedList = readProjectsArray(reader);
            
        }
        finally
        {
            reader.close();
        }
        
        return loadedList;
    }
    
    static private LinkedList<ProjectListingStruct> readProjectsArray(JsonReader reader) throws IOException
    {
        LinkedList<ProjectListingStruct> entries = new LinkedList<>();
        
        reader.beginArray();
        while (reader.hasNext())
        {
            entries.add(readProject(reader));
        }
        reader.endArray();
        return entries;
    }
    
    static private ProjectListingStruct readProject(JsonReader reader) throws IOException
    {
        String categoryName = null;
        String projectName = null;
        
        reader.beginObject();
        while (reader.hasNext())
        {
            String key = reader.nextName();
            
            if(key.equals(JSON_KEY_CATEGORY))
            {
                categoryName = reader.nextString();
            }
            else if(key.equals(JSON_KEY_PROJECT))
            {
                projectName = reader.nextString();
            }
            else
            {
                reader.skipValue();
            }
        }
        reader.endObject();
        
        
        return new ProjectListingStruct(categoryName, projectName);
    }
    
}
