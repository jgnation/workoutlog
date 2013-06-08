package com.jgnation.workoutLog.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
//import com.test.model.WishItem;
//import com.test.model.WishList;
import com.jgnation.workoutLog.WishItem;
import com.jgnation.workoutLog.WishList;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static String DATABASE_PATH = "";
	private final Context mContext;
	private SQLiteDatabase mDataBase; 
	
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "workoutLogDB.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;
    
    // the DAO object we use to access the SimpleData table
    private Dao<Profile, Integer> profileDao = null;
    private Dao<Routine, Integer> routineDao = null;
    private Dao<Section, Integer> sectionDao = null;
    private Dao<Exercise, Integer> exerciseDao = null;
    private Dao<WorkoutItem, Integer> workoutItemDao = null;
    private Dao<WorkoutItemSet, Integer> workoutItemSetDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        //DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        
        this.mContext = context;
    }

    
    public void createDataBase() throws IOException
    {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try 
            {
                //Copy the database from assests
                copyDataBase();
                Log.e("DatabaseHelper", "createDatabase database created");
            } 
            catch (IOException mIOException) 
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }
        //Check that the database exists here: /data/data/your package/databases/Da Name
        private boolean checkDataBase()
        {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
            return dbFile.exists();
        }

        //Copy the database from assets
        private void copyDataBase() throws IOException
        {
            InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer))>0)
            {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        }

        //Open the database, so we can query it
        public boolean openDataBase() throws SQLException
        {
            String mPath = DATABASE_PATH + DATABASE_NAME;
            //Log.v("mPath", mPath);
            mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            return mDataBase != null;
        }

        @Override
        public synchronized void close() 
        {
            if(mDataBase != null)
                mDataBase.close();
            super.close();
        }
        
        
        //////////////////////////////////
    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
       /* try {
            TableUtils.createTable(connectionSource, Profile.class);
            TableUtils.createTable(connectionSource, Routine.class);
            TableUtils.createTable(connectionSource, Section.class);
            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, WorkoutItem.class);
            TableUtils.createTable(connectionSource, WorkoutItemSet.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }  */      
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<String>(); 
            switch(oldVersion) 
            {
              case 1: 
                  //allSql.add("alter table AdData add column `new_col` VARCHAR");
                  //allSql.add("alter table AdData add column `new_col2` VARCHAR");
            }
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }
        
    }

    public Dao<Profile, Integer> getProfileDao() {
        if (null == profileDao) {
            try {
                profileDao = getDao(Profile.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return profileDao;
    }
    
    public Dao<Routine, Integer> getRoutineDao() {
        if (null == routineDao) {
            try {
                routineDao = getDao(Routine.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return routineDao;
    }
    
    public Dao<Section, Integer> getSectionDao() {
        if (null == sectionDao) {
            try {
                sectionDao = getDao(Section.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return sectionDao;
    }

    public Dao<Exercise, Integer> getExerciseDao() {
        if (null == exerciseDao) {
            try {
            	exerciseDao = getDao(Exercise.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return exerciseDao;
    }
    
    public Dao<WorkoutItem, Integer> getWorkoutItemDao() {
        if (null == workoutItemDao) {
            try {
            	workoutItemDao = getDao(WorkoutItem.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return workoutItemDao;
    }

    public Dao<WorkoutItemSet, Integer> getWorkoutItemSetDao() {
        if (null == workoutItemSetDao) {
            try {
            	workoutItemSetDao = getDao(WorkoutItemSet.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return workoutItemSetDao;
    }
}
