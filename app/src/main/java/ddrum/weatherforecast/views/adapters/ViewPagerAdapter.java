package ddrum.weatherforecast.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ddrum.weatherforecast.views.fragments.HomeFragment;
import ddrum.weatherforecast.views.fragments.ProfileFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
