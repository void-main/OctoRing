/*
 * Copyright (C) 2013 Void Main Studio 
 * Project:Octoller
 * Author: voidmain
 * Create Date: May 6, 201310:57:42 AM
 */
package me.voidmain.apps.octoring.utils;

import me.voidmain.apps.octoring.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Helper function to create the transcation
 * 
 * @Project Octoller
 * @Package cn.ac.iscas.iel.vr.octoller.utils
 * @Class FragmentTransactionHelper
 * @Date May 6, 2013 10:57:42 AM
 * @author voidmain
 * @version
 * @since
 */
public class FragmentTransactionUtilities {

	public static void transTo(Fragment fragment, Fragment newFragment,
			String name, boolean forward) {
		transTo(fragment.getActivity(), newFragment, name, forward);
	}

	public static void transTo(FragmentActivity activity, Fragment newFragment,
			String name, boolean forward) {
		FragmentTransaction ft = activity.getSupportFragmentManager()
				.beginTransaction();
		if (forward) {
			ft.setCustomAnimations(android.R.anim.fade_in, 
					android.R.anim.fade_out);
		} else {
			ft.setCustomAnimations(android.R.anim.fade_in, 
					android.R.anim.fade_out);
		}

		ft.replace(R.id.app_root, newFragment, name);

		ft.commit();
	}

}
