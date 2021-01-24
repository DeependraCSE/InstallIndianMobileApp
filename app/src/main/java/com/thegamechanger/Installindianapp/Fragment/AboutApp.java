package com.thegamechanger.Installindianapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thegamechanger.Installindianapp.BuildConfig;
import com.thegamechanger.Installindianapp.Helper.Constant;
import com.thegamechanger.Installindianapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutApp extends Fragment implements View.OnClickListener {
    View view;
    Context context;

    Button btn_feedback;
    TextView tv_app_virsion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.frag_about_app,container,false);
        context = getActivity();

        btn_feedback = view.findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(this);

        tv_app_virsion = view.findViewById(R.id.tv_app_virsion);
        tv_app_virsion.setText("" + BuildConfig.VERSION_NAME);
        return view;
    }

    private void SendMailFeedback(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Constant.APP_MAIL, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Notes");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Team,");
        startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_feedback){
            SendMailFeedback();
        }
    }
}
