package com.samplesidemenu;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class BaseFragment extends Fragment {


    public void clearStack() {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).clearStack();
            }
        }
    }


    public void setTitle(String mTitle) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setTitle(mTitle);
            }
        }
    }

    public static Fragment getCurrentTopFragment(FragmentManager fm) {
        int stackCount = fm.getBackStackEntryCount();
        if (stackCount > 0) {
            FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(stackCount - 1);
            return fm.findFragmentByTag(backEntry.getName());
        } else {
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment f : fragments) {
                    if (f != null && !f.isHidden()) {
                        return f;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
