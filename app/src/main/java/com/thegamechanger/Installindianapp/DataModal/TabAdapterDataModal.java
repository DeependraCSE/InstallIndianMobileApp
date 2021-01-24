package com.thegamechanger.Installindianapp.DataModal;

import androidx.fragment.app.Fragment;

public class TabAdapterDataModal {
    String title;
    Fragment fragment;

    public TabAdapterDataModal(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
