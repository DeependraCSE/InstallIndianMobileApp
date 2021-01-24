package com.thegamechanger.Installindianapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.thegamechanger.Installindianapp.DataModal.AppDetail;
import com.thegamechanger.Installindianapp.Fragment.AppListByType;
import com.thegamechanger.Installindianapp.Fragment.Dashboard;
import com.thegamechanger.Installindianapp.Helper.Constant;
import com.thegamechanger.Installindianapp.LocalDataBase.DBHelper;
import com.thegamechanger.Installindianapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //String TAG = "MainActivity";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });

        InsertData();
    }

    public void InsertData(){
        long count = dbHelper.AppDetailCount();
        if (count > 0){
            LoadHomeFragment();
        }else {
            new InsertAppDetail(this).execute();
        }
    }

    public void LoadHomeFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Dashboard(), Constant.HomeFragment).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constant.HomeFragment);
        if (null != fragment && fragment.isVisible()){
            if (null != Dashboard.pager && Dashboard.pager.getCurrentItem() == Dashboard.SelectedTab){
                super.onBackPressed();
            }else {
                Dashboard.pager.setCurrentItem(Dashboard.SelectedTab);
            }
        }else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack();
            }else {
                LoadHomeFragment();
            }
        }
    }

    public class InsertAppDetail extends AsyncTask<Void,Void,Void>{
        Context context;
        private ProgressDialog progressDialog;

        public InsertAppDetail(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("app_list.csv"), "UTF-8"));
                List<AppDetail> objects = new ArrayList<>();
                String mLine;
                reader.readLine();  // remove header (column name)
                while ((mLine = reader.readLine()) != null) {
                    String[] row = mLine.split(",");
                    objects.add(new AppDetail(row[0],row[1],row[2],row[3],row[4]));
                }
                dbHelper.InsertUpdateAppDetailList(objects);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,null,"Please Wait",true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing() )
                progressDialog.dismiss();
            LoadHomeFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
