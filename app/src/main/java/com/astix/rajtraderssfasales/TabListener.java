package com.astix.rajtraderssfasales;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;

import com.astix.rajtraderssfasales.R;

public class TabListener implements ActionBar.TabListener {

	private Fragment fragment;
	
	// The contructor.
	public TabListener(Fragment fragment) {
		this.fragment = fragment;
	}


	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		fragmentTransaction.replace(R.id.mysummary_bytab, fragment);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		fragmentTransaction.replace(R.id.mysummary_bytab, fragment);
	}


}
