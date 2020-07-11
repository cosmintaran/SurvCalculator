package com.cosmintaran.survcalculator.MapControl.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cosmintaran.survcalculator.Map.helperClasses.Line2D;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import com.cosmintaran.survcalculator.R;

public class DropPerpendicularFragment extends Fragment {

    private EditText editTextX1;
    private EditText editTextY1;
    private EditText editTextX2;
    private EditText editTextY2;
    private EditText _pointX;
    private EditText _pointY;


    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_drop_perpendicular , container , false);
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view , savedInstanceState);
        editTextX1 = view.findViewById(R.id.editTxtNorth1);
        editTextX2 = view.findViewById(R.id.editTxtNorth2);
        _pointX = view.findViewById(R.id.editTxtNorth3);
        editTextY1 = view.findViewById(R.id.editTxtEast1);
        editTextY2 = view.findViewById(R.id.editTxtEast2);
        _pointY = view.findViewById(R.id.editTxtEast3);

        Button _clearBtt = view.findViewById(R.id.clearBtt);
        _clearBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                editTextX1.setText("");
                editTextX2.setText("");
                _pointX.setText("");
                editTextY1.setText("");
                editTextY2.setText("");
                _pointY.setText("");
            }
        });

        Button _calculateBtt = view.findViewById(R.id.calculateBtt);
        _calculateBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                DoCalculation();
            }
        });

    }

    private void DoCalculation() {

        if(!dataIsValid()) {
            Toast.makeText(getContext(),"Please fill all fields",Toast.LENGTH_SHORT ).show();
            return;
        }

        Line2D line = new Line2D(new SrvPoint2D(Double.parseDouble(editTextX1.getText().toString()),
                Double.parseDouble(editTextY1.getText().toString())),
                new SrvPoint2D(Double.parseDouble(editTextX2.getText().toString()),
                        Double.parseDouble(editTextY2.getText().toString())));

        SrvPoint2D rez = line.pointIntersection(new SrvPoint2D(Double.parseDouble(_pointX.getText().toString()),
                Double.parseDouble(_pointY.getText().toString())));

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
        dlgAlert.setMessage(String.format("X = %.3f\nY = %.3f", rez.X, rez.Y));
        dlgAlert.setTitle("Result");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private boolean dataIsValid() {

        if(TextUtils.isEmpty(editTextX1.getText()) || TextUtils.isEmpty(editTextX2.getText()) || TextUtils.isEmpty(_pointX.getText()) ||
                TextUtils.isEmpty(_pointY.getText()) || TextUtils.isEmpty(editTextY1.getText()) || TextUtils.isEmpty(editTextY2.getText())){
            return false;
        }

        return true;
    }

}
