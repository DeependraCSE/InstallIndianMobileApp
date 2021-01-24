package com.thegamechanger.Installindianapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thegamechanger.Installindianapp.Activity.MainActivity;
import com.thegamechanger.Installindianapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Splash extends Fragment {
    View view;
    Context context;
    Handler handler;
    Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.frag_splash,container,false);
        context = getActivity();
        PostDelay();
        return view;
    }

    public void PostDelay(){
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,MainActivity.class));
                getActivity().finish();
            }
        };

        handler.postDelayed(runnable,2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != handler)
            handler.removeCallbacks(runnable);
    }
}
