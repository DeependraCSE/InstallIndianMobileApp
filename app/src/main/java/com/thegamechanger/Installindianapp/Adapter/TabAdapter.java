package com.thegamechanger.Installindianapp.Adapter;

import com.thegamechanger.Installindianapp.DataModal.TabAdapterDataModal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<TabAdapterDataModal> fragmentList = new ArrayList<>();
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<TabAdapterDataModal> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
