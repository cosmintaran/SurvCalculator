package com.cosmintaran.survcalculator.MapControl.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cosmintaran.survcalculator.R;

public class PointFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {

        if (savedInstanceState == null) {
            FragmentTransaction childFragTrans = getChildFragmentManager().beginTransaction();
            childFragTrans.replace(R.id.fragment_container_points,new ForPointIntersectionFragment());
            childFragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            childFragTrans.addToBackStack(null);
            childFragTrans.commit();
        }
        return inflater.inflate(R.layout.fragment_points, container,false);

    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view , savedInstanceState);

        Spinner spinner = getView().findViewById(R.id.points_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.pointOperations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected ( AdapterView<?> parent , View view , int position , long id ) {
        FragmentTransaction childFragTrans = getChildFragmentManager().beginTransaction();
        childFragTrans.replace(R.id.fragment_container_points,new ForPointIntersectionFragment());
        childFragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        childFragTrans.addToBackStack(null);
        childFragTrans.commit();
    }

    @Override
    public void onNothingSelected ( AdapterView<?> parent ) {

    }
}
