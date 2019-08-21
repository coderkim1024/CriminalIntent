package nucky.example.com.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 单例设计模式
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    //唯一的获取方法
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //私有构造方法
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        /*for(int i = 0;i < 100;i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }*/
    }
    //
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    //返回列表
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    //返回某个id对应的Crime对象
    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
