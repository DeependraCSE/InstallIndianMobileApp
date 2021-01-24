package com.thegamechanger.Installindianapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.thegamechanger.Installindianapp.Adapter.TabAdapter;
import com.thegamechanger.Installindianapp.DataModal.AppType;
import com.thegamechanger.Installindianapp.DataModal.TabAdapterDataModal;
import com.thegamechanger.Installindianapp.Helper.Constant;
import com.thegamechanger.Installindianapp.LocalDataBase.DBHelper;
import com.thegamechanger.Installindianapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Dashboard extends Fragment {
    String TAG = "Dashboard";
    View view;
    Context context;
    DBHelper dbHelper;
    public static ViewPager pager;
    TabLayout tab_layout;
    private TabAdapter adapter;
    public static final int SelectedTab = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.frag_dashboard,container,false);
        context = getActivity();
        dbHelper = new DBHelper(context);

        pager = view.findViewById(R.id.pager);
        tab_layout = view.findViewById(R.id.tab_layout);
        adapter = new TabAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(pager);

        GetAppType();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void GetAppType(){
        List<AppType> object = dbHelper.GetAppType();
        List<TabAdapterDataModal> fragmentList = new ArrayList<>();
        if (null != object && object.size()>0){
            fragmentList.add(new TabAdapterDataModal("Installed",new Installed()));
            for (AppType appType : object){
                Bundle bundle = new Bundle();
                bundle.putString(Constant.TypeId,appType.getType_id());
                Fragment fragment = new AppListByType();
                fragment.setArguments(bundle);
                fragmentList.add(new TabAdapterDataModal(appType.getType_name(),fragment));
            }
            adapter.setFragmentList(fragmentList);
            pager.setCurrentItem(SelectedTab);
            pager.setOffscreenPageLimit(fragmentList.size());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.comp_sugg:
                SendMailComplaintSuggestion();
                break;
            case R.id.share_app:
                ShareApp();
                break;
            case R.id.rate_us:
                RateUs();
                break;
            case R.id.about_app:
                getFragmentManager().beginTransaction().replace(R.id.container,new  AboutApp()).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendMailComplaintSuggestion(){
        Intent emailIntent = new  Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",Constant.APP_MAIL, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Complaint & Suggestion Install Indian App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Team,");
        startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)));
    }

    private void ShareApp(){
        Intent shareIntent = new  Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.str_share_app) + " " + Constant.APP_LINK);
        startActivity(Intent.createChooser(shareIntent,"Share " + getString(R.string.app_name)));
    }

    private void RateUs(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
    }
}
