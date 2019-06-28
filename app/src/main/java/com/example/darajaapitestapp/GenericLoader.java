package com.example.darajaapitestapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class GenericLoader extends DialogFragment {

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.generic_loading_dialog_layout,null));
        return builder.create();
    }

    public void showDialog(FragmentManager fragmentManager, String title){
        DialogFragment dialogFragment = new GenericLoader();
        TextView textView = getActivity().findViewById(R.id.generic_loading_text_view);
        textView.setText(title);
        dialogFragment.setCancelable(true);
        dialogFragment.show(fragmentManager,getActivity().getResources().getString(R.string.app_name));
    }
}
