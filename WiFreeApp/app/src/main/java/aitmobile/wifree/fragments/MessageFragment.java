package aitmobile.wifree.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import aitmobile.wifree.MainActivity;
import aitmobile.wifree.R;


public class MessageFragment extends DialogFragment {

    private String nPass;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
        final EditText etName = (EditText) dialogLayout.findViewById(R.id.etName);
        alertDialogBuilder.setView(dialogLayout);


        alertDialogBuilder.setTitle(R.string.net_title);
        //alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(!(etName.getText().toString().compareTo("") == 0 )) {
                    ((MainActivity) getActivity()).addCurrNetwork(etName.getText().toString());
                }

            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        return alertDialogBuilder.create();

    }

}




