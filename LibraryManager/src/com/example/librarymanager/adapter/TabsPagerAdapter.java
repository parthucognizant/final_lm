package com.example.librarymanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.librarymanager.EconomicsActivity;
import com.example.librarymanager.ScienceActivity;
import com.example.librarymanager.SocialActivity;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new EconomicsActivity();
		case 1:
			// Games fragment activity
			return new ScienceActivity();
		case 2:
			// Movies fragment activity
			return new SocialActivity();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}

