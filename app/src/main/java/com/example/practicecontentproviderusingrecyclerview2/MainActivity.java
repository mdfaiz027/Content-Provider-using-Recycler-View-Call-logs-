package com.example.practicecontentproviderusingrecyclerview2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CallLogsModel> callLogsModelList = new ArrayList<>();
    CallLogsModel callLogsModel;
    CallLogsAdapter callLogsAdapter;

    ContentResolver contentResolver;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        callLogsAdapter = new CallLogsAdapter(getApplicationContext(), callLogsModelList);
        recyclerView.setAdapter(callLogsAdapter);
    }

    public void load(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 200);
        }else{
            importAllCallLogs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 200:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    importAllCallLogs();
                }else{
                    Toast.makeText(this, "Permission Denied For Call logs", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void importAllCallLogs() {

        contentResolver = getContentResolver();

        //Path of all contacts
        Uri uri = CallLog.Calls.CONTENT_URI;
        //Projections means columns of the contacts - Which columns you want to access
        String[] projections = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.DURATION};
        //String sortOrder = CallLog.Calls.NUMBER +" DESC";

        cursor = contentResolver.query(uri,projections, null, null, null);

        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                @SuppressLint("Range") int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));

                callLogsModelList.add(new CallLogsModel(name, number, date, duration, type));
                callLogsAdapter.notifyDataSetChanged();

            }
        }
        else{
            Toast.makeText(this, "No call logs found", Toast.LENGTH_SHORT).show();
        }
    }
}