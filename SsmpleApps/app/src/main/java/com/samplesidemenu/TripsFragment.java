package com.samplesidemenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TripsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.trips_fragment, null);
        ((Button) mView.findViewById(R.id.btnAddTrip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddTipsClicked();
            }
        });
        return mView;
    }

    private void btnAddTipsClicked() {
        AddTripFragment mObjFragment = new AddTripFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContent, mObjFragment, getString(R.string.inner_frag)).addToBackStack(null)
                .commitAllowingStateLoss();

    }
}
