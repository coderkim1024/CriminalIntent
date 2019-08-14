package nucky.example.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra("crime id");
        return CrimeFragment.newInstance(crimeId);
        //return new CrimeFragment();
    }

    public static Intent newIntent(Context context, UUID crimeId){
        Intent intent = new Intent(context,CrimeActivity.class);
        intent.putExtra("crime id",crimeId);
        return intent;
    }
}
