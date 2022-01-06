package com.example.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;

public interface DialogExt{

     static void confirmDialog(String itemId, int position, Activity activity, Database database, Adapter adapter, ArrayList<Model> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.delete_confirmation );
        builder.setMessage(R.string.are_you_sure_delete_task);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.deleteOneRow(itemId);
                adapter.deleteItem(position);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}
