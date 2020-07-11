package com.cosmintaran.survcalculator.MapControl.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cosmintaran.survcalculator.Map.helperClasses.LineIntersectionResult;
import com.cosmintaran.survcalculator.Map.helperClasses.Line2D;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import com.cosmintaran.survcalculator.Map.helperClasses.TypeIntersection;
import com.cosmintaran.survcalculator.R;

public class ForPointIntersectionFragment extends Fragment {

    private EditText editTextX1;
    private EditText editTextY1;
    private EditText editTextX2;
    private EditText editTextY2;

    private EditText editTextX3;
    private EditText editTextY3;
    private EditText editTextX4;
    private EditText editTextY4;

    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_for_point_intersection , container , false);
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view , savedInstanceState);
        editTextX1 = view.findViewById(R.id.editTxtNorth1);
        editTextX2 = view.findViewById(R.id.editTxtNorth2);
        editTextX3 = view.findViewById(R.id.editTxtNorth3);
        editTextX4 = view.findViewById(R.id.editTxtNorth4);

        editTextY1 = view.findViewById(R.id.editTxtEast1);
        editTextY2 = view.findViewById(R.id.editTxtEast2);
        editTextY3 = view.findViewById(R.id.editTxtEast3);
        editTextY4 = view.findViewById(R.id.editTxtEast4);
        Button _clearBtt = view.findViewById(R.id.clearBtt);
        _clearBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                editTextX1.setText("");
                editTextX2.setText("");
                editTextX3.setText("");
                editTextX4.setText("");
                editTextY1.setText("");
                editTextY2.setText("");
                editTextY3.setText("");
                editTextY4.setText("");
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

    @SuppressLint("DefaultLocale")
    private void DoCalculation ( ) {

        if(!dataIsValid()) {
            Toast.makeText(getContext(),"Please fill all fields",Toast.LENGTH_SHORT ).show();
            return;
        }
        Line2D line1 = new Line2D(new SrvPoint2D(Double.parseDouble(editTextX1.getText().toString()),
                Double.parseDouble(editTextY1.getText().toString())),
                new SrvPoint2D(Double.parseDouble(editTextX2.getText().toString()),
                        Double.parseDouble(editTextY2.getText().toString())));


        Line2D line2 = new Line2D(new SrvPoint2D(Double.parseDouble(editTextX3.getText().toString()),
                Double.parseDouble(editTextY3.getText().toString())),
                new SrvPoint2D(Double.parseDouble(editTextX4.getText().toString()),
                        Double.parseDouble(editTextY4.getText().toString())));

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
        dlgAlert.setTitle("Result");
        LineIntersectionResult intersection = line1.lineIntersection(line2);
        if(intersection.getType() == TypeIntersection.NoIntersection){
            dlgAlert.setMessage("Lines do not intersect");
        }
        else {
            dlgAlert.setMessage(String.format("X = %.3f\nY = %.3f\nIntersection type: %s"
                    , intersection.getX(), intersection.getY(), intersection.getType().toString()));
        }
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }

    private boolean dataIsValid() {

        if(TextUtils.isEmpty(editTextX1.getText()) || TextUtils.isEmpty(editTextX2.getText()) || TextUtils.isEmpty(editTextX3.getText()) ||
                TextUtils.isEmpty(editTextX4.getText()) || TextUtils.isEmpty(editTextY1.getText()) || TextUtils.isEmpty(editTextY2.getText()) ||
                TextUtils.isEmpty(editTextY3.getText()) || TextUtils.isEmpty(editTextY4.getText())){
            return false;
        }

        return true;
    }
}
