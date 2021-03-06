package com.example.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.item.ItemJob;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ixidev.gdpr.GDPRChecker;

public class PopUpAds {
    private InterstitialAd mInterstitialAd;
    private final String TAG = "myapp";
    public static void showInterstitialAds(final Context context, final int adapterPosition, final RvOnClickListener clickListener, ItemJob singleItem) {
        if (Constant.isInterstitial) {
            Constant.AD_COUNT += 1;
            if (Constant.AD_COUNT == Constant.AD_COUNT_SHOW) {
                if (Constant.isAdMobInterstitial) {
                    AdRequest builder = new AdRequest.Builder().build();
//                    final InterstitialAd mInterstitial = new InterstitialAd(context);
//                    mInterstitial.setAdUnitId(Constant.interstitialId);
                    GDPRChecker.Request request = GDPRChecker.getRequest();
                    if (request == GDPRChecker.Request.NON_PERSONALIZED) {
                        Bundle extras = new Bundle();
                        extras.putString("npa", "1");
//                        builder.addNetworkExtrasBundle(AdMobAdapter.class, extras);
                    }
//                    mInterstitial.loadAd(builder.build());
                    Constant.AD_COUNT = 0;
                    InterstitialAd.load(context, Constant.interstitialId, builder, new InterstitialAdLoadCallback() {
                        public InterstitialAd mInterstitialAd;

                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            super.onAdLoaded(interstitialAd);
                            mInterstitialAd = interstitialAd;
                            if (mInterstitialAd != null){
                                mInterstitialAd.show((Activity) context);
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                        }
                    });
                    /*mInterstitial.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            mInterstitial.show();
                        }

                        @Override
                        public void onAdClosed() {
                            clickListener.onItemClick(adapterPosition);
                            super.onAdClosed();
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            clickListener.onItemClick(adapterPosition);
                            super.onAdFailedToLoad(i);
                        }
                    });*/
                } else {
                    Constant.AD_COUNT = 0;
                    final com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(context, Constant.interstitialId);
                    InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            clickListener.onItemClick(adapterPosition,singleItem);
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {
                            clickListener.onItemClick(adapterPosition,singleItem);
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            interstitialAd.show();
                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    };
                    com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig loadAdConfig = interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).withCacheFlags(CacheFlag.ALL).build();
                    interstitialAd.loadAd(loadAdConfig);
                }
            } else {
                clickListener.onItemClick(adapterPosition,singleItem);
            }
        } else {
            clickListener.onItemClick(adapterPosition,singleItem);
        }
    }
}
