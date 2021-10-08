package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bihar.land_records.MyApplication;
import com.bihar.land_records.R;
import com.bihar.land_records.SignInActivity;
import com.example.item.ItemJob;
import com.example.util.ApplyJob;
import com.example.util.Constant;
import com.example.util.NetworkUtils;
import com.example.util.PopUpAds;
import com.example.util.RvOnClickListener;
import com.example.util.SaveClickListener;
import com.example.util.SaveJob;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ItemJob> dataList;
    private Context mContext;
    private RvOnClickListener clickListener;
    NativeAdsManager nativeAdsManager;
    List<NativeAd> mAdItems;
    private static final int AD_frequency = 4;
    private static final int Post_type = 0;
    private static final int AD_type = 1;
    public HomeJobAdapter(Context context, ArrayList<ItemJob> dataList, NativeAdsManager nativeAdsManager,RvOnClickListener clickListener) {
        this.dataList = dataList;
        this.mContext = context;
        this.clickListener = clickListener;
        this.nativeAdsManager = nativeAdsManager;
        mAdItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_item_new, parent, false);
//        if(viewType == AD_type){
//            NativeAdLayout nativeAdLayout = (NativeAdLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ad_layout,parent,false);
//            return new unifiedFbHolder(nativeAdLayout);
//        }
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        int viewtype = getItemViewType(position);
//        if(viewtype == AD_type){
//            NativeAd ad;
//            if(mAdItems.size()>position/AD_frequency){
//                ad = mAdItems.get(position/AD_frequency);
//
//            }else {
//                ad =nativeAdsManager.nextNativeAd();
//                if(ad!=null){
//                    if(!ad.isAdInvalidated()){
//                        mAdItems.add(ad);
//                    }
//                }
//            }
//            unifiedFbHolder adholder = (unifiedFbHolder) viewHolder;
//            adholder.adChoiceContainer.removeAllViews();
//            if(ad!=null) {
//                adholder.tvAdTitle.setText(ad.getAdvertiserName());
//                adholder.tvAdBody.setText(ad.getAdBodyText());
//                adholder.tvAdSocialContext.setText(ad.getAdSocialContext());
//                adholder.tvAdSponseredLabel.setText("sponsered");
//                adholder.btnCallToAction.setText(ad.getAdCallToAction());
//                adholder.btnCallToAction.setVisibility(ad.hasCallToAction()?View.VISIBLE:View.INVISIBLE);
//
//                AdOptionsView adOptionsView = new AdOptionsView(mContext,ad,adholder.nativeAdLayout);
//                adholder.adChoiceContainer.addView(adOptionsView,0);
//
//                List<View> clickableViews = new ArrayList<>();
//                clickableViews.add(adholder.ivAdIcon);
//                clickableViews.add(adholder.mvAdMedia);
//                clickableViews.add(adholder.btnCallToAction);
//                ad.registerViewForInteraction(adholder.nativeAdLayout,adholder.mvAdMedia
//                        ,adholder.ivAdIcon,clickableViews);
//
//
//
//
//            }
//        }
//        else
            {
            final ItemRowHolder holder = (ItemRowHolder) viewHolder;
            final ItemJob singleItem = dataList.get(position);
            holder.jobTitle.setText(singleItem.getJobName());
            holder.jobType.setText(singleItem.getJobType());
            holder.jobAddress.setText(singleItem.getJobAddress());
            Picasso.get().load(singleItem.getJobImage()).placeholder(R.drawable.placeholder).into(holder.jobImage);


            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUpAds.showInterstitialAds(mContext, holder.getAdapterPosition(), clickListener, singleItem);
                    clickListener.onItemClick(position,singleItem);
                }
            });

            holder.btnApplyJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.getInstance().getIsLogin()) {
                        if (NetworkUtils.isConnected(mContext)) {
                            new ApplyJob(mContext).userApply(singleItem.getId());
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, mContext.getString(R.string.need_login), Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(mContext, SignInActivity.class);
                        intentLogin.putExtra("isOtherScreen", true);
                        mContext.startActivity(intentLogin);
                    }
                }
            });

            if (singleItem.isJobFavourite()) {
                holder.imageFav.setImageResource(R.drawable.ic_fav_hover);
            } else {
                holder.imageFav.setImageResource(R.drawable.ic_fav);
            }

            holder.imageFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.getInstance().getIsLogin()) {
                        if (NetworkUtils.isConnected(mContext)) {
                            SaveClickListener saveClickListener = new SaveClickListener() {
                                @Override
                                public void onItemClick(boolean isSave, String message) {
                                    if (isSave) {
                                        holder.imageFav.setImageResource(R.drawable.ic_fav_hover);
                                    } else {
                                        holder.imageFav.setImageResource(R.drawable.ic_fav);
                                    }
                                }
                            };
                            new SaveJob(mContext).userSave(singleItem.getId(), saveClickListener);
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, mContext.getString(R.string.need_login), Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(mContext, SignInActivity.class);
                        intentLogin.putExtra("isOtherScreen", true);
                        mContext.startActivity(intentLogin);
                    }
                }
            });

            switch (singleItem.getJobType()) {
                case Constant.JOB_TYPE_HOURLY:
                    holder.jobType.setTextColor(mContext.getResources().getColor(R.color.hourly_time_text));
                    holder.cardViewType.setCardBackgroundColor(mContext.getResources().getColor(R.color.hourly_time_bg));
                    break;
                case Constant.JOB_TYPE_HALF:
                    holder.jobType.setTextColor(mContext.getResources().getColor(R.color.half_time_text));
                    holder.cardViewType.setCardBackgroundColor(mContext.getResources().getColor(R.color.half_time_bg));
                    break;
                case Constant.JOB_TYPE_FULL:
                    holder.jobType.setTextColor(mContext.getResources().getColor(R.color.full_time_text));
                    holder.cardViewType.setCardBackgroundColor(mContext.getResources().getColor(R.color.full_time_bg));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return position % AD_frequency == 0 && position !=0 ? AD_type:Post_type;
    }

    public void setOnItemClickListener(RvOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, jobAddress, jobType;
        LinearLayout lyt_parent;
        Button btnApplyJob;
        CardView cardViewType;
        CircleImageView jobImage;
        ImageView imageFav;

        ItemRowHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.text_job_title);
            jobType = itemView.findViewById(R.id.text_job_type);
            jobAddress = itemView.findViewById(R.id.text_job_address);
            lyt_parent = itemView.findViewById(R.id.rootLayout);
            cardViewType = itemView.findViewById(R.id.cardJobType);
            jobImage = itemView.findViewById(R.id.image_job);
            imageFav = itemView.findViewById(R.id.imageFav);
            btnApplyJob = itemView.findViewById(R.id.btn_apply_job);
        }
    }
}
