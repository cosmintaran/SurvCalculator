package com.cosmintaran.survcalculator.MapControl.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cosmintaran.survcalculator.R;

public class DistanceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_distance , container , false);
    }
}
