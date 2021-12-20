package com.example.todoapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyLocalBoundService  extends Service {
    public IBinder localBinder = new MyLocalBinder();
    public MyLocalBoundService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    public String getSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.US);
        return simpleDateFormat.format(new Date());
    }

    public class MyLocalBinder extends Binder {
        MyLocalBoundService getBoundService() {
            return MyLocalBoundService.this;
        }
    }
}
