package com.dev.holker.wholesale.model;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.dev.holker.wholesale.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class InfoDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_info, null);
        builder.setView(view);

        final EditText editTextName = view.findViewById(R.id.dialog_info_name);
        final EditText editTextDescr = view.findViewById(R.id.dialog_info_descr);
        Button buttonSubmit = view.findViewById(R.id.dialog_info_submit);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        editTextName.setText(currentUser.getString("name"));
        editTextDescr.setText(currentUser.getString("description"));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.put("name", editTextName.getText().toString());
                currentUser.put("description", editTextDescr.getText().toString());
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        InfoDialog.this.getDialog().cancel();
                    }
                });
            }
        });


        return builder.create();
    }
}
