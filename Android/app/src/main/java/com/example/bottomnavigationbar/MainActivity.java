package com.example.bottomnavigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;

import com.example.bottomnavigationbar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new InformationFragment());

        //setContentView(R.layout.activity_main);

        binding.bottomNavigationView.setOnItemSelectedListener(item->{
            switch(item.getItemId()){

                case R.id.Today:
                    replaceFragment(new TodayFragment());
                    break;

                case R.id.Medication:
                    replaceFragment(new MedicationFragment());
                    break;

                case R.id.Information:
                    replaceFragment(new InformationFragment());
                    break;
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}