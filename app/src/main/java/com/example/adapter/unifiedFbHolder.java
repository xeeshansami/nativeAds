package com.example.adapter;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bihar.land_records.R;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdLayout;

public class unifiedFbHolder extends RecyclerView.ViewHolder {

    public NativeAdLayout nativeAdLayout;
    public MediaView mvAdMedia,ivAdIcon;
    public TextView tvAdTitle,tvAdBody,tvAdSocialContext,tvAdSponseredLabel;
    Button btnCallToAction;
    LinearLayout adChoiceContainer;

    public unifiedFbHolder(NativeAdLayout nativeAdLayout) {
        super(nativeAdLayout);
        this.nativeAdLayout = nativeAdLayout;

        mvAdMedia = nativeAdLayout.findViewById(R.id.native_ad_media);
        tvAdTitle = nativeAdLayout.findViewById(R.id.native_ad_title);
        tvAdBody = nativeAdLayout.findViewById(R.id.native_ad_body);
        tvAdSocialContext = nativeAdLayout.findViewById(R.id.native_ad_social_context);
        tvAdSponseredLabel = nativeAdLayout.findViewById(R.id.native_ad_sponsored_label);
        btnCallToAction = nativeAdLayout.findViewById(R.id.native_ad_call_to_action);
        ivAdIcon = nativeAdLayout.findViewById(R.id.native_ad_icon);
        adChoiceContainer = nativeAdLayout.findViewById(R.id.ad_choices_container);
    }
}
