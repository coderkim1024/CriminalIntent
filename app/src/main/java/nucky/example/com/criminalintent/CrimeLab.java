package nucky.example.com.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchema.CrimeBaseHelper;
import database.CrimeDbSchema.CrimeCursorWrapper;
import database.CrimeDbSchema.CrimeDbSchema;
import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

/**
 * 单例设计模式
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    //private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //唯一的获取方法
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //私有构造方法
    private CrimeLab(Context context){
        //此处创建数据库
        mContext=context.getApplicationContext();
        mDatabase=new CrimeBaseHelper(mContext).getWritableDatabase();
        //mCrimes = new ArrayList<>();
        /*for(int i = 0;i < 100;i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }*/
    }
    //
    public void addCrime(Crime c){
        //mCrimes.add(c);
        ContentValues values=getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }

    public File getPhotoFile(Crime crime){
        File fileDir=mContext.getFilesDir();
        return new File(fileDir,crime.getPhotoFilename());
    }
    public void updateCrime(Crime c){
        String uuidString=c.getId().toString();
        ContentValues values=getContentValues(c);
        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID+"=?",new String[]{uuidString});
    }
    public CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor=mDatabase.query(CrimeTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new CrimeCursorWrapper(cursor);
    }

    //返回列表
    public List<Crime> getCrimes(){
        //return mCrimes;
        //return new ArrayList<>();
        List<Crime> crimes=new ArrayList<>();
        CrimeCursorWrapper cursorWrapper=queryCrimes(null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return crimes;
    }
    //返回某个id对应的Crime对象
    public Crime getCrime(UUID id){
        /*for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }*/
        //return null;
        CrimeCursorWrapper cursorWrapper=queryCrimes(CrimeTable.Cols.UUID+"=?",new String[]{id.toString()});
        try{
            if(cursorWrapper.getCount()==0){
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getCrime();
        }finally {
            cursorWrapper.close();
        }
    }
    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID,crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE,crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);
        contentValues.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return contentValues;
    }
}
