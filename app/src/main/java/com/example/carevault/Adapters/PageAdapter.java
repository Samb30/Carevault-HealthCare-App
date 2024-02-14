package com.example.carevault.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.carevault.tab.CancelledFragment;
import com.example.carevault.tab.CompletedFragment;
import com.example.carevault.tab.UpcomingFragment;

public class PageAdapter  extends FragmentStateAdapter {
    public PageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpcomingFragment();
            case 1:
                return new CompletedFragment();
            case 2:
                return new CancelledFragment();
            default:
                return new UpcomingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
