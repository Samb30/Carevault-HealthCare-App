package com.example.carevault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.carevault.fragments.CalenderFragment;
import com.example.carevault.fragments.DoctorsFragment;
import com.example.carevault.fragments.HomeFragment;
import com.example.carevault.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        bottomNavigationView=findViewById(R.id.bnview);
        load(new HomeFragment());
        bottomNavigationView
                .setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id=item.getItemId();
                        if(id==R.id.home1){
                            load(new HomeFragment());
                            return true;
                        }else if(id==R.id.home2){
                            load(new HomeFragment());
                            return true;
                        }else if(id==R.id.hom3){
                            load(new CalenderFragment());
                            return true;
                        } else if (id==R.id.memo) {
                            load(new UserFragment());
                            return true;
                        } else if(id==R.id.doc){
                            load(new DoctorsFragment());
                            return true;
                        }
                        return false;
                    }
                });
    }
    public void load(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tab,fragment);
        fragmentTransaction.commit();
    }
}