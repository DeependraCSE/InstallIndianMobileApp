package com.thegamechanger.Installindianapp.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thegamechanger.Installindianapp.DataModal.AppDetail;
import com.thegamechanger.Installindianapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppDetailAdpt extends RecyclerView.Adapter<AppDetailAdpt.ViewHolder> {
    String TAG = "AppDetailAdpt";
    Context context;
    List<AppDetail>object;
    LayoutInflater inflater;

    public AppDetailAdpt(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adpt_app_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AppDetail obj = object.get(position);

        holder.iv_icon.setImageResource(context.getResources().getIdentifier(obj.getIcon(),"drawable",context.getPackageName()));
        holder.tv_name.setText(obj.getName());
        if (obj.isInstalled()){
            holder.btn_install_open.setBackgroundResource(R.drawable.btn_open);
            holder.btn_install_open.setText(context.getResources().getString(R.string.open));
            holder.btn_install_open.setTextColor(context.getResources().getColor(R.color.text_open));
        }else {
            holder.btn_install_open.setBackgroundResource(R.drawable.btn_install);
            holder.btn_install_open.setText(context.getResources().getString(R.string.install));
            holder.btn_install_open.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.btn_install_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appPackageName = obj.getPackage_name(); // package name of the app
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                }else {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != object)
            return object.size();
        return 0;
    }

    public void setObject(List<AppDetail> object) {
        this.object = object;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        Button btn_install_open;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            btn_install_open = itemView.findViewById(R.id.btn_install_open);
        }
    }
}
