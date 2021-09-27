package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class JobDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ItemJob> dataList;
    private Context mContext;
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private final int ITEM_BANNER_AD = 2;
    private RvOnClickListener clickListener;
    private List<Object> recyclerItems = new ArrayList<>();

    public JobDetailAdapter(Context context, List<Object> dataList, ArrayList<ItemJob> mListItem) {
        this.recyclerItems = dataList;
        this.dataList = mListItem;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_item_new, parent, false);
            return new ItemRowHolder(v);
        } else if (viewType == ITEM_BANNER_AD) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_ads_item, parent, false);
            return new BannerAdViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new ProgressViewHolder(v);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == VIEW_TYPE_ITEM) {
//            if (viewHolder.itemView.getParent() == null) {
                final ItemRowHolder holder = (ItemRowHolder) viewHolder;
                final ItemJob singleItem = (ItemJob) recyclerItems.get(position);
                holder.jobTitle.setText(singleItem.getJobName());
                holder.jobType.setText(singleItem.getJobType());
                holder.jobAddress.setText(singleItem.getJobAddress());
                Picasso.get().load(singleItem.getJobImage()).placeholder(R.drawable.placeholder).into(holder.jobImage);
                holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUpAds.showInterstitialAds(mContext, holder.getAdapterPosition(), clickListener);
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
//            }
        } else if (viewHolder.getItemViewType() == ITEM_BANNER_AD) {
            try {

            }catch (Exception e){}
        }
    }
    public static boolean hasChildren(ViewGroup viewGroup) {
        return viewGroup.getChildCount() > 0;
    }
    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void hideHeader() {
        try {
            ProgressViewHolder.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    private boolean isHeader(int position) {
        return position == recyclerItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0)
            return ITEM_BANNER_AD;
        else return isHeader(position) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    public void setOnItemClickListener(RvOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class BannerAdViewHolder extends RecyclerView.ViewHolder {

        public BannerAdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        private AdView mAdView;
        TextView jobTitle, jobAddress, jobType;
        LinearLayout lyt_parent;
        Button btnApplyJob;
        CardView cardViewType;
        CircleImageView jobImage;
        ImageView imageFav;

        ItemRowHolder(View itemView) {
            super(itemView);
            mAdView = itemView.findViewById(R.id.adView);
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

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        static ProgressBar progressBar;

        ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (recyclerView.getParent() != null) {
            ViewGroup parent = (ViewGroup) recyclerView.getParent();
            parent.removeAllViews();
        }
    }
}
