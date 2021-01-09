package recahrge.plans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragment = new ArrayList<>();
    private final List<String> tiles = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tiles.get(position);
    }

    public void addFragment(Fragment fragment1, String title){

        fragment.add(fragment1);
        tiles.add(title);

    }
}
