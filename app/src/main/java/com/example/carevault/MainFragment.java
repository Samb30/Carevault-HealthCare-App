package com.example.carevault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.carevault.fragments.ArticleFragment;
import com.example.carevault.fragments.CalenderFragment;
import com.example.carevault.fragments.DoctorsFragment;
import com.example.carevault.fragments.HomeFragment;
import com.example.carevault.fragments.MessageFragment;
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
                            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                @Override
                                public void onBackStackChanged() {
                                    updateBottomNavigationSelectedItem();
                                }
                            });
                            load(new HomeFragment());
                            return true;
                        }else if(id==R.id.home2){
                            load(new ArticleFragment());
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
        if(!fragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.tab, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private void updateBottomNavigationSelectedItem() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.bnview);
        if (currentFragment instanceof HomeFragment)
            bottomNavigationView.setSelectedItemId(R.id.home1);
        else if (currentFragment instanceof ArticleFragment)
            bottomNavigationView.setSelectedItemId(R.id.home2);
        else if (currentFragment instanceof CalenderFragment)
            bottomNavigationView.setSelectedItemId(R.id.hom3);
        else if (currentFragment instanceof UserFragment)
            bottomNavigationView.setSelectedItemId(R.id.memo);
        else if (currentFragment instanceof DoctorsFragment)
            bottomNavigationView.setSelectedItemId(R.id.doc);
    }
    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.home1) {
            super.onBackPressed();
            finish();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.home1);
        }
    }
}