package com.jgnation.workoutLog.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;

import android.content.Context;
import android.util.Log;

//import com.test.model.WishList;

public class DatabaseManager {

    static private DatabaseManager instance;
    private DatabaseHelper helper;
    
    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
        //jag - i'm not sure why, but the line below ended the issue where errors were saying tables did not exist
        //helper.getWritableDatabase();
        
        try 
        {
            helper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e("DatabaseManager", mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
    }

    private DatabaseHelper getHelper() {
        return helper;
    }
    
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = null;
        try {
            profiles = getHelper().getProfileDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
    
    public Profile getProfileById(int profileId) {
        Profile profile = null;
        try {
            profile = getHelper().getProfileDao().queryForId(profileId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
    
    public List<Routine> getAllRoutines() {
        List<Routine> routines = null;
        try {
            routines = getHelper().getRoutineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routines;
    }
    
    public Routine getRoutineById(int routineId) {
        Routine routine = null;
        try {
            routine = getHelper().getRoutineDao().queryForId(routineId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routine;
    }
    
    public Section getSectionById(int sectionId) {
        Section section = null;
        try {
            section = getHelper().getSectionDao().queryForId(sectionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }
    
    public Exercise getExerciseById(int exerciseId) {
    	Exercise exercise = null;
        try {
        	exercise = getHelper().getExerciseDao().queryForId(exerciseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercise;
    }
    
    public WorkoutItem getWorkoutItemById(int workoutItemId) {
    	WorkoutItem workoutItem = null;
    	try {
    		workoutItem = getHelper().getWorkoutItemDao().queryForId(workoutItemId);
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return workoutItem;
    }
    
    public WorkoutItemSet getWorkoutItemSetById(int workoutItemSetId) {
    	WorkoutItemSet workoutItemSet = null;
    	try {
    		workoutItemSet = getHelper().getWorkoutItemSetDao().queryForId(workoutItemSetId);
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return workoutItemSet;
    }
    
    //raw query here?
    public List<Routine> getAllRoutinesByProfileId(int profileId) {
        List<Routine> routines = null;
        try {
            QueryBuilder <Routine, Integer> queryBuilder = getHelper().getRoutineDao().queryBuilder();
            queryBuilder.where().eq("profile", profileId);
            routines = getHelper().getRoutineDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routines;
    }
    
    //raw query here?
    public List<Section> getAllSectionsByRoutineId(int routineId) {
        List<Section> sections = null;
        try {
            QueryBuilder <Section, Integer> queryBuilder = getHelper().getSectionDao().queryBuilder();
            queryBuilder.where().eq("routine", routineId);
            sections = getHelper().getSectionDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }
    
    public List<Exercise> getAllExercisesBySectionId(int sectionId) {
        List<Exercise> exercises = null;
        try {
            QueryBuilder <Exercise, Integer> queryBuilder = getHelper().getExerciseDao().queryBuilder();
            queryBuilder.where().eq("section", sectionId);
            exercises = getHelper().getExerciseDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }
    
    public List <WorkoutItemSet> getAllWorkoutItemSetsByExerciseId(int exerciseId) {
    	List<WorkoutItemSet> workoutItemSets = null;
        try {
            QueryBuilder <WorkoutItemSet, Integer> queryBuilder = getHelper().getWorkoutItemSetDao().queryBuilder();
            queryBuilder.where().eq("exercise", exerciseId);
            workoutItemSets = getHelper().getWorkoutItemSetDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workoutItemSets;
    }
    
    public List <WorkoutItemSet> getAllWorkoutItemSetsByWorkoutItemId(int workoutItemId) {
    	List<WorkoutItemSet> workoutItemSets = null;
        try {
            QueryBuilder <WorkoutItemSet, Integer> queryBuilder = getHelper().getWorkoutItemSetDao().queryBuilder();
            queryBuilder.where().eq("workoutItem", workoutItemId);
            workoutItemSets = getHelper().getWorkoutItemSetDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workoutItemSets;
    }
    
    public List <WorkoutItem> getAllWorkoutItemsByExerciseId(int exerciseId) {
    	List<WorkoutItem> workoutItems = null;
        try {
        	QueryBuilder <WorkoutItem, Integer> queryBuilder = getHelper().getWorkoutItemDao().queryBuilder();
            queryBuilder.where().eq("exercise", exerciseId);
            queryBuilder.orderBy("internalDate", false);
            workoutItems = getHelper().getWorkoutItemDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workoutItems;
    }
    
    public void addProfile(Profile p) {
        try {
            getHelper().getProfileDao().create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProfile(Profile p) {
        try {
            getHelper().getProfileDao().delete(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addRoutine(Routine r) {
        try {
            getHelper().getRoutineDao().create(r);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addSection(Section s) {
        try {
            getHelper().getSectionDao().create(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addExercise(Exercise ex) {
        try {
            getHelper().getExerciseDao().create(ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addWorkoutItem(WorkoutItem w) {
        try {
        	w.setInternalDate(new Date());
            getHelper().getWorkoutItemDao().create(w);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addWorkoutItemSet(WorkoutItemSet s) {
        try {
            getHelper().getWorkoutItemSetDao().create(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoutine(Routine r) {
        try {
            getHelper().getRoutineDao().delete(r);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteSection(Section s) {
        try {
            getHelper().getSectionDao().delete(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteExercise(Exercise ex) {
        try {
            getHelper().getExerciseDao().delete(ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteWorkoutItem(WorkoutItem w) {
    	try {
            getHelper().getWorkoutItemDao().delete(w);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteWorkoutItemSet(WorkoutItemSet w) {
    	try {
            getHelper().getWorkoutItemSetDao().delete(w);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ForeignCollection<WorkoutItemSet> getEmptyWorkoutItemForeignCollectionMember()
    {
    	ForeignCollection<WorkoutItemSet> list = null;
    	try {
			list = getHelper().getWorkoutItemDao().getEmptyForeignCollection("workoutItemSets");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	catch (Exception i)
    	{
    		i.printStackTrace();
    	}
    	return list;
    }
}

