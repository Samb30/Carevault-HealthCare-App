package com.example.carevault;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.carevault.fragments.ArticleFragment;
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

    DrawerLayout drawerLayout;

    ImageButton buttonDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        bottomNavigationView=findViewById(R.id.bnview);
        loadFragment(new HomeFragment());

        drawerLayout = findViewById(R.id.drawerlayout);
        buttonDrawerToggle = findViewById(R.id.imageView3);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        bottomNavigationView
                .setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {

                            case R.id.home1:
                                fragment = new HomeFragment();
                                break;
                            case R.id.home2:
                                fragment = new CalenderFragment();
                                break;
                            case R.id.hom3:
                                fragment = new ArticleFragment();
                                break;
                            case R.id.memo:
                                fragment = new UserFragment();
                                break;
                            case R.id.doc:
                                fragment = new DoctorsFragment();
                                break;
                        }
                        return loadFragment(fragment);
                    }
                });
    }

//                    int id=item.getItemId();
//                    if(id==R.id.home1){
//                        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                            @Override
//                            public void onBackStackChanged() {
//                                updateBottomNavigationSelectedItem();
//                            }
//                        });
//                        load(new HomeFragment());
//                        return true;
//                    }else if(id==R.id.home2){
//                        load(new ArticleFragment());
//                        return true;
//                    }else if(id==R.id.hom3){
//                        load(new CalenderFragment());
//                        return true;
//                    } else if (id==R.id.memo) {
//                        load(new UserFragment());
//                        return true;
//                    } else if(id==R.id.doc){
//                        load(new DoctorsFragment());
//                        return true;
//                    }
//                    return false;
//                });
//    }
    public boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tab, fragment)
                    .commit();
            return true;
        }
        return false;
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