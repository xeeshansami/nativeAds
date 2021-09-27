package com.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihar.land_records.R;
import com.example.adapter.SkillsAdapter;
import com.example.item.ItemJob;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobDetailsFragment extends Fragment {




    /*//fb native ad

    private final String TAG = "SignInActivity".getClass().getSimpleName();
    private NativeAd nativeAd;

    private NativeAdLayout nativeAdLayout;
    private NativeAdLayout adView;


    private void loadNativeAd(final View view) {
        // Instantiate a NativeAd bject.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(getContext(), "1800549386770946_1800550993437452");

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");

                if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                inflateAd(nativeAd,view); // Inflate NativeAd into a container, same as in previous code examples
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd,view);

                if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                nativeAd.downloadMedia();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }




        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

    }


    private void inflateAd(NativeAd nativeAd,View rootView) {

        nativeAd.unregisterView();
        AudienceNetworkAds.initialize(getContext());
        // Add the Ad view into the ad container.
        nativeAdLayout = rootView.findViewById(R.id.native_ad_container);
      //  LayoutInflater inflater = LayoutInflater.from(JobDetailsFragment.this);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (NativeAdLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = rootView.findViewById(R.id.ad_choices_container);
   //     AdOptionsView adOptionsView = new AdOptionsView(JobDetailsFragment.this, nativeAd, nativeAdLayout);
        AdOptionsView adOptionsView = new AdOptionsView(getContext(), nativeAd, nativeAdLayout);

        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);

    }


    //fb native ad end*/



    WebView webView;
    TextView textSalary, textJobQualification, textWorkDay, textWorkTime, textExp, textType;
    ItemJob itemJob;
    RecyclerView recyclerView;
    ArrayList<String> mSkills;
    //TemplateView templateView;

    public static JobDetailsFragment newInstance(ItemJob itemJob) {
        JobDetailsFragment f = new JobDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("itemJob", itemJob);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_job_details, container, false);
        if (getArguments() != null) {
            itemJob = (ItemJob) getArguments().getSerializable("itemJob");
        }
        webView = rootView.findViewById(R.id.text_job_description);
        textSalary = rootView.findViewById(R.id.text_job_salary);
        textJobQualification = rootView.findViewById(R.id.text_job_qualification);
        textSalary = rootView.findViewById(R.id.text_job_salary);
        textWorkDay = rootView.findViewById(R.id.text_job_work_day);
        textWorkTime = rootView.findViewById(R.id.text_job_work_time);
        textExp = rootView.findViewById(R.id.text_job_exp);
        textType = rootView.findViewById(R.id.text_job_type);
        recyclerView = rootView.findViewById(R.id.rv_skills);
        mSkills = new ArrayList<>();
        //templateView = rootView.findViewById(R.id.my_template);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);
        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = itemJob.getJobDesc();
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/custom.otf\")}body{font-family: MyFont;color: #9E9E9E;text-align:left;font-size:14px;margin-left:0px}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        webView.loadDataWithBaseURL(null, text, mimeType, encoding, null);
        textSalary.setText(itemJob.getJobSalary());
        textJobQualification.setText(itemJob.getJobQualification());
        textType.setText(itemJob.getJobType());
        textWorkDay.setText(itemJob.getJobWorkDay());
        textWorkTime.setText(itemJob.getJobWorkTime());
        textExp.setText(itemJob.getJobExperience());

        if (!itemJob.getJobSkill().isEmpty()) {
            mSkills = new ArrayList<>(Arrays.asList(itemJob.getJobSkill().split(",")));
            SkillsAdapter skillsAdapter = new SkillsAdapter(getActivity(), mSkills);
            recyclerView.setAdapter(skillsAdapter);
        }

        return rootView;
    }


}
