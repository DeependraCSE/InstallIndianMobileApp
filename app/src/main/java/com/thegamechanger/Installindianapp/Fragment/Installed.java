package com.thegamechanger.Installindianapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thegamechanger.Installindianapp.Adapter.AppDetailAdpt;
import com.thegamechanger.Installindianapp.DataModal.AppDetail;
import com.thegamechanger.Installindianapp.Helper.Constant;
import com.thegamechanger.Installindianapp.LocalDataBase.DBHelper;
import com.thegamechanger.Installindianapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Installed extends Fragment {
    String TAG = "Installed";
    View view;
    Context context;
    RecyclerView recycler_view;
    AppDetailAdpt adpt;
    DBHelper dbHelper;
    LinearLayout ll_no_data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.frag_recyclerview,container,false);
        context = getActivity();
        dbHelper = new DBHelper(context);
        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new GridLayoutManager(context,3));
        adpt = new AppDetailAdpt(context);
        recycler_view.setAdapter(adpt);

        ll_no_data = view.findViewById(R.id.ll_no_data);
        return view;
    }

    @Override
    public void onResume() {
        new SetData(context, "0").execute();
        super.onResume();
    }

    private class SetData extends AsyncTask<Void,Void,Void>{
        Context context;
        String type_id;
        ProgressDialog progressDialog;
        List<AppDetail> objects,tempObj;
        public SetData(Context context, String type_id) {
            this.context = context;
            this.type_id = type_id;
            tempObj = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, null, "Please Wait", true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            adpt.setObject(tempObj);
            if (tempObj.size() > 0){
                recycler_view.setVisibility(View.VISIBLE);
                ll_no_data.setVisibility(View.GONE);
            }else {
                recycler_view.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            objects = dbHelper.GetAppDetail(type_id);
            //Log.e(TAG,"size : " + objects.size());
            for (int i=0; i<objects.size(); i++){
                AppDetail appDetail = objects.get(i);
                //Log.e(TAG,"Name : " + appDetail.getName());
                try {
                    context.getPackageManager().getApplicationInfo(appDetail.getPackage_name(), 0);
                    appDetail.setInstalled(true);
                    tempObj.add(appDetail);
                    //Log.e(TAG,"added : " + appDetail.getName());
                }catch (PackageManager.NameNotFoundException e) {
                    //Log.e(TAG,"Removed : " + appDetail.getName());
                }

            }
            return null;
        }
    }
}
