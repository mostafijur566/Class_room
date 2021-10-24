package com.example.dr_benigno_aldana_mobile_application;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog_2 extends AppCompatDialogFragment {

    private EditText dialog_class_name, dialog_section, dialog_subject, dialog_room, dialog_code;
    private ExampleDialog2Listener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_2, null);

        builder.setView(view)
                .setTitle("Create Class")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create Class", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String class_name = dialog_class_name.getText().toString().trim();
                        String class_section = dialog_section.getText().toString().trim();
                        String class_subject = dialog_subject.getText().toString().trim();
                        String class_room = dialog_room.getText().toString().trim();
                        String class_code = dialog_code.getText().toString().trim();

                        listener.applyTexts(class_name, class_section, class_subject, class_room, class_code);
                    }
                });

        dialog_class_name = view.findViewById(R.id.dialog_class_name);
        dialog_section = view.findViewById(R.id.dialog_section);
        dialog_subject = view.findViewById(R.id.dialog_subject);
        dialog_room = view.findViewById(R.id.dialog_room);
        dialog_code = view.findViewById(R.id.dialog_code);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialog2Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialog2Listener");
        }
    }

    public  interface ExampleDialog2Listener{
        void applyTexts(String name, String section, String subject, String room, String code);
    }
}
