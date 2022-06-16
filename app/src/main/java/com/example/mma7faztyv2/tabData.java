package com.example.mma7faztyv2;

import androidx.fragment.app.Fragment;

public class tabData {

    private String name;
    private Fragment fragment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public tabData(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

}
