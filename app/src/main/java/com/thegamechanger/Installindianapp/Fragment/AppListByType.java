package com.thegamechanger.Installindianapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AppListByType extends Fragment {
    String TAG = "AppListByType";

    View view;
    Context context;
    RecyclerView recycler_view;
    AppDetailAdpt adpt;
    DBHelper dbHelper;
    String type_id;
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
        Bundle bundle = getArguments();
        if (null != bundle && bundle.containsKey(Constant.TypeId)){
         type_id = bundle.getString(Constant.TypeId);
            //Log.e(TAG,"type_id : " +type_id);
        }
        return view;
    }

    @Override
    public void onResume() {
        if (null != type_id){
            new SetData(context, type_id).execute();
        }else {
            new SetData(context, "0").execute();
        }
        //Log.e(TAG,"onResume type_id : " +type_id);
        super.onResume();
    }

    private class SetData extends AsyncTask<Void,Void,Void>{
        Context context;
        String type_id;
        ProgressDialog progressDialog;
        List<AppDetail> objects;
        public SetData(Context context, String type_id) {
            this.context = context;
            this.type_id = type_id;
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
            adpt.setObject(objects);
            if (objects.size() > 0){
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
            for (int i=0; i<objects.size(); i++){
                AppDetail appDetail = objects.get(i);
                try {
                    context.getPackageManager().getApplicationInfo(appDetail.getPackage_name(), 0);
                    appDetail.setInstalled(true);
                }
                catch (PackageManager.NameNotFoundException e) {
                    appDetail.setInstalled(false);
                }
                objects.set(i,appDetail);
            }
            return null;
        }
    }
}
