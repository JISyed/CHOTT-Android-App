package io.github.jisyed.chott.session;

import android.content.Context;

import java.util.LinkedList;

import io.github.jisyed.chott.data.ProjectListingStruct;
import io.github.jisyed.chott.data.ProjectSessionEntry;
import io.github.jisyed.chott.data.utility.CategoryType;
import io.github.jisyed.chott.data.utility.ChottJsonUtility;

/**
 * Created by jishenaz on 11/9/15.
 * 
 */
public class ChottSessionSingleton
{
    //
    // Data
    //
    
    private CategoryType currentCategory;
    
    private LinkedList<ProjectListingStruct> projectsListArt;
    private LinkedList<ProjectListingStruct> projectsListCode;
    private LinkedList<ProjectListingStruct> projectsListMusic;
    private LinkedList<ProjectListingStruct> projectsListWriting;
    
    private LinkedList<ProjectSessionEntry> entriesListArt;
    private LinkedList<ProjectSessionEntry> entriesListCode;
    private LinkedList<ProjectSessionEntry> entriesListMusic;
    private LinkedList<ProjectSessionEntry> entriesListWriting;
    
    static private Context currentContext = null;
    
    
    //
    // Singleton Stuff
    //
    
    private static ChottSessionSingleton instance = null;
    
    // Private ctor
    private ChottSessionSingleton()
    {
        this.currentCategory = CategoryType.Art;    // Doesn't matter what the value is
    }
    
    // Singleton Getter
    public static ChottSessionSingleton I()
    {
        if(ChottSessionSingleton.instance == null)
        {
            ChottSessionSingleton.instance = new ChottSessionSingleton();
        }
        if(ChottSessionSingleton.instance.projectsListArt == null)
        {
            ChottSessionSingleton.instance.setupEveryProjectList();
        }
        if(ChottSessionSingleton.instance.entriesListArt == null)
        {
            ChottSessionSingleton.instance.setupEveryEntryList();
        }
        return ChottSessionSingleton.instance;
    }
    
    
    //
    // Methods
    //
    
    private void setupEveryProjectList()
    {
        /*
        this.projectsListArt = new LinkedList<>();
        this.projectsListCode = new LinkedList<>();
        this.projectsListMusic = new LinkedList<>();
        this.projectsListWriting = new LinkedList<>();
        
        
        this.projectsListArt.add(new ProjectListingStruct(CategoryType.Art, "Sweet Painting"));
        this.projectsListArt.add(new ProjectListingStruct(CategoryType.Art, "3D Modeling"));
        this.projectsListArt.add(new ProjectListingStruct(CategoryType.Art, "Something Secret"));
        this.projectsListArt.add(new ProjectListingStruct(CategoryType.Art, "Abstract Art"));
        this.projectsListArt.add(new ProjectListingStruct(CategoryType.Art, "Sketches"));
        
        
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "Big Game Engine"));
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "Random Unity Game"));
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "Client Work"));
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "New Website"));
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "Library You Wanted"));
        this.projectsListCode.add(new ProjectListingStruct(CategoryType.Code, "Some cool App"));
        
        
        this.projectsListMusic.add(new ProjectListingStruct(CategoryType.Music, "AudioTool Project"));
        this.projectsListMusic.add(new ProjectListingStruct(CategoryType.Music, "Random music App"));
        this.projectsListMusic.add(new ProjectListingStruct(CategoryType.Music, "Make someone sing"));
        this.projectsListMusic.add(new ProjectListingStruct(CategoryType.Music, "Learn music programs"));
        this.projectsListMusic.add(new ProjectListingStruct(CategoryType.Music, "Messing around"));
        
        this.projectsListWriting.add(new ProjectListingStruct(CategoryType.Writing, "Dream Novel"));
        this.projectsListWriting.add(new ProjectListingStruct(CategoryType.Writing, "Romantic Poetry"));
        this.projectsListWriting.add(new ProjectListingStruct(CategoryType.Writing, "Blog post"));
        this.projectsListWriting.add(new ProjectListingStruct(CategoryType.Writing, "Life Journal"));
        //*/
        
        
        //*
        this.projectsListArt = ChottJsonUtility.loadListOfProjects(ChottSessionSingleton.currentContext,
                                                                   ChottJsonUtility.getProjectFileNameByCategory(CategoryType.Art)
        );
        this.projectsListCode = ChottJsonUtility.loadListOfProjects(ChottSessionSingleton.currentContext,
                                                                   ChottJsonUtility.getProjectFileNameByCategory(CategoryType.Code)
        );
        this.projectsListMusic = ChottJsonUtility.loadListOfProjects(ChottSessionSingleton.currentContext,
                                                                   ChottJsonUtility.getProjectFileNameByCategory(CategoryType.Music)
        );
        this.projectsListWriting = ChottJsonUtility.loadListOfProjects(ChottSessionSingleton.currentContext,
                                                                   ChottJsonUtility.getProjectFileNameByCategory(CategoryType.Writing)
        );
        //*/
        
        
    }
    
    
    private void setupEveryEntryList()
    {
        /*
        this.entriesListArt = new LinkedList<>();
        this.entriesListCode = new LinkedList<>();
        this.entriesListMusic = new LinkedList<>();
        this.entriesListWriting = new LinkedList<>();
        
        long s1 = 345032345L;
        long e1 = 358003245L;
        
        long s2 = 3425232345L;
        long e2 = 4353402345L;
        
        long s3 = 34522342345L;
        long e3 = 39832022030L;
        
        long s4 = 432523234341L;
        long e4 = 932409523453L;
        
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Sweet Painting", new SessionLengthStruct(e1-s1), new Date(s1), new Date(e1)));
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Sweet Painting", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Sweet Painting", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Sweet Painting", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "3D Modeling", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "3D Modeling", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Something Secret", new SessionLengthStruct(e1-s1), new Date(s1), new Date(e1)));
    
        this.entriesListArt.add(new ProjectSessionEntry(CategoryType.Art, "Sketches", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
    
    
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Big Game Engine", new SessionLengthStruct(e1-s1), new Date(s1), new Date(e1)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Big Game Engine", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Big Game Engine", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Big Game Engine", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Random Unity Game", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Random Unity Game", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "Random Unity Game", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "New Website", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListCode.add(new ProjectSessionEntry(CategoryType.Code, "New Website", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
    
    
    
        this.entriesListMusic.add(new ProjectSessionEntry(CategoryType.Music, "AudioTool Project", new SessionLengthStruct(e1-s1), new Date(s1), new Date(e1)));
        this.entriesListMusic.add(new ProjectSessionEntry(CategoryType.Music, "AudioTool Project", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListMusic.add(new ProjectSessionEntry(CategoryType.Music, "AudioTool Project", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListMusic.add(new ProjectSessionEntry(CategoryType.Music, "AudioTool Project", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
    
    
    
        this.entriesListWriting.add(new ProjectSessionEntry(CategoryType.Writing, "Life Journal", new SessionLengthStruct(e1-s1), new Date(s1), new Date(e1)));
        this.entriesListWriting.add(new ProjectSessionEntry(CategoryType.Writing, "Life Journal", new SessionLengthStruct(e2-s2), new Date(s2), new Date(e2)));
        this.entriesListWriting.add(new ProjectSessionEntry(CategoryType.Writing, "Life Journal", new SessionLengthStruct(e3-s3), new Date(s3), new Date(e3)));
        this.entriesListWriting.add(new ProjectSessionEntry(CategoryType.Writing, "Life Journal", new SessionLengthStruct(e4-s4), new Date(s4), new Date(e4)));
        //*/
    
    
        //*
        this.entriesListArt = ChottJsonUtility.loadListOfEntries(ChottSessionSingleton.currentContext,
                                                                 ChottJsonUtility.getEntryFileNameByCategory(CategoryType.Art)
        );
        this.entriesListCode = ChottJsonUtility.loadListOfEntries(ChottSessionSingleton.currentContext,
                                                                 ChottJsonUtility.getEntryFileNameByCategory(CategoryType.Code)
        );
        this.entriesListMusic = ChottJsonUtility.loadListOfEntries(ChottSessionSingleton.currentContext,
                                                                 ChottJsonUtility.getEntryFileNameByCategory(CategoryType.Music)
        );
        this.entriesListWriting = ChottJsonUtility.loadListOfEntries(ChottSessionSingleton.currentContext,
                                                                 ChottJsonUtility.getEntryFileNameByCategory(CategoryType.Writing)
        );
        //*/
        
    }
    
    
    // Removes every object in the lists in hopes that the GC will clean it, but I don't know if it will
    public void dereferenceListsForGarbageCollector()
    {
        this.projectsListArt.clear();
        this.projectsListCode.clear();
        this.projectsListMusic.clear();
        this.projectsListWriting.clear();
        this.projectsListArt = null;
        this.projectsListCode = null;
        this.projectsListMusic = null;
        this.projectsListWriting = null;
        this.entriesListArt.clear();
        this.entriesListCode.clear();
        this.entriesListMusic.clear();
        this.entriesListWriting.clear();
        this.entriesListArt = null;
        this.entriesListCode = null;
        this.entriesListMusic = null;
        this.entriesListWriting = null;
    
        ChottSessionSingleton.currentContext = null;
    }
    
    
    
    public void addNewEntry(ProjectSessionEntry newEntry)
    {
        LinkedList<ProjectSessionEntry> designatedList = this.getEntryListOfCurrentCategory();
        designatedList.add(newEntry);
    }
    
    
    // Can only be called by the current activity
    static public void setCurrentContext(Context nextActivity)
    {
        ChottSessionSingleton.currentContext = nextActivity;
    }
    
    
    //
    // Accessors
    //
    
    public CategoryType getCurrentCategory()
    {
        return this.currentCategory;
    }
    
    public void setCurrentCategory(CategoryType newCategory)
    {
        this.currentCategory = newCategory;
    }
    
    
    public LinkedList<ProjectListingStruct> getProjectsListArt()
    {
        return this.projectsListArt;
    }
    
    public LinkedList<ProjectListingStruct> getProjectsListCode()
    {
        return this.projectsListCode;
    }
    
    public LinkedList<ProjectListingStruct> getProjectsListMusic()
    {
        return this.projectsListMusic;
    }
    
    public LinkedList<ProjectListingStruct> getProjectsListWriting()
    {
        return this.projectsListWriting;
    }
    
    public LinkedList<ProjectSessionEntry> getEntryListOfCurrentCategory()
    {
        LinkedList<ProjectSessionEntry> designatedList = null;
    
        switch (this.currentCategory)
        {
            case Art:
                designatedList = this.entriesListArt;
                break;
            case Code:
                designatedList = this.entriesListCode;
                break;
            case Music:
                designatedList = this.entriesListMusic;
                break;
            case Writing:
                designatedList = this.entriesListWriting;
                break;
        }
        
        return designatedList;
    }
}
