package com.dev.holker.wholesale.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.dev.holker.wholesale.R;
import com.dev.holker.wholesale.presenters.DialogLocationPresenter;

public class LocationDialog extends DialogFragment {

    Activity activityLocation;
    Dialog dialog;
    Spinner spinnerCountry;
    Spinner spinnerCity;
    Button buttonSubmit;
    String country;
    Location location;
    EditText editTextStreet;
    NoticeDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_location, null);
        builder.setView(view);

        //presenter
        final DialogLocationPresenter presenter = new DialogLocationPresenter(view);

        //Find
        spinnerCountry = (Spinner) view.findViewById(R.id.dialog_location_s_country);
        spinnerCity = (Spinner) view.findViewById(R.id.dialog_location_s_city);
        editTextStreet = (EditText) view.findViewById(R.id.dialog_location_et_street);
        buttonSubmit = (Button) view.findViewById(R.id.dialog_location_btn_submit);

        buttonSubmit.setEnabled(false);


        spinnerCountry.setAdapter(presenter.getCountryAdapter());

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                spinnerCity.setAdapter(presenter.getCityAdapter(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonSubmit.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.apply(spinnerCountry.getSelectedItem().toString(), spinnerCity.getSelectedItem().toString()
                        , editTextStreet.getText().toString());
                LocationDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public interface NoticeDialogListener {
        void apply(String country, String city, String street);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
}
