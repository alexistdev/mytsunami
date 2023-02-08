package com.malik.mytstunami.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.malik.mytstunami.API.APIService;
import com.malik.mytstunami.API.NoConnectivityException;
import com.malik.mytstunami.Login;
import com.malik.mytstunami.MapsActivity;
import com.malik.mytstunami.R;
import com.malik.mytstunami.model.GempaModel;
import com.malik.mytstunami.utils.AlertReciever;


import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class Home extends Fragment {
    private ImageView mPeta;
    private TextView mWaktu,mLokasi,mKedalaman,mMagnitudo,mRichter;
    private ProgressDialog pDialog;
    private LinearLayout mBox;
    private Button mMatikan;
    private PendingIntent pendingIntent = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview= inflater.inflate(R.layout.fragment_home, container, false);
        dataInit(mview);
        setData();
        return mview;
    }

    private void AlarmSet()
    {
        Calendar calendar = Calendar.getInstance();
        int jam = calendar.get(Calendar.HOUR_OF_DAY);
        int menit = calendar.get(Calendar.MINUTE);
        onTimeSet(jam,menit+1);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        mWaktu.setText(timeText);
    }
    public void setData() {
        tampilkanDialog();
        try {
            Call<GempaModel> call= APIService.Factory.create(getContext()).dataGempa();
            call.enqueue(new Callback<GempaModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GempaModel> call, Response<GempaModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            mBox.setVisibility(View.VISIBLE);
                            mPeta.setVisibility(View.VISIBLE);
                            mMatikan.setVisibility(View.VISIBLE);
                            AlarmSet();
                            mLokasi.setText("Lokasi : "+ response.body().getLatitude()+","+response.body().getLongitude());
                            mKedalaman.setText("Kedalaman :"+ response.body().getKedalaman());
                            mMagnitudo.setText("Magnitudo :" + response.body().getMagnitudo());
                            mRichter.setText(response.body().getMagnitudo() + "SR");
                            mMatikan.setOnClickListener(v -> {
                                cancelAlarm();
                                pesan("alarm sudah dimatikan");
                            });
                            mPeta.setOnClickListener(v -> {
                                Intent intent = new Intent(getActivity(), MapsActivity.class);
                                startActivity(intent);
                            });
                        }
                    }else{
                        pesan("data kosong");
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GempaModel> call, Throwable t) {
                    sembunyikanDialog();
                    if(t instanceof NoConnectivityException) {
                        pesan("Offline, cek koneksi internet anda!");
                    }
                }
            });

        } catch (Exception e) {
            sembunyikanDialog();
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }

    public void onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }

    private void dataInit(View mview) {
        mPeta = mview.findViewById(R.id.btn_peta);
        mLokasi = mview.findViewById(R.id.txt_lokasi);
        mWaktu = mview.findViewById(R.id.txt_waktu);
        mKedalaman = mview.findViewById(R.id.txt_kedalaman);
        mMagnitudo = mview.findViewById(R.id.txt_magnitudo);
        mRichter = mview.findViewById(R.id.txt_richter);
        mBox = mview.findViewById(R.id.linearLayout);
        mMatikan = mview.findViewById(R.id.btn_matikan);
        mBox.setVisibility(View.INVISIBLE);
        mPeta.setVisibility(View.INVISIBLE);
        mMatikan.setVisibility(View.INVISIBLE);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReciever.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
//            pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
            pendingIntent = PendingIntent.getActivity
                    (getContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        }


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

    private void tampilkanDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void sembunyikanDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void pesan(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}