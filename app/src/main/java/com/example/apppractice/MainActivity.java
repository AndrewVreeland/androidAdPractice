package com.example.apppractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private AdView myBannerAd;
    private InterstitialAd myInterstitialAd;

    private RewardedAd myRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
    setUpBannerAd();
        setupInterstitialAd();
        setupRewardedAd();

    }
    private void setUpBannerAd(){
        myBannerAd = findViewById(R.id.bannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myBannerAd.loadAd(adRequest);
    }
    private void setupInterstitialAd(){

        AdRequest interstitialAdRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712",
                interstitialAdRequest, new InterstitialAdLoadCallback(){
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);

                        Log.d(TAG, loadAdError.toString());
                        myInterstitialAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        myInterstitialAd = interstitialAd;

                        myInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                                Log.d(TAG, "Ad was clicked");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                myInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);

                                Log.e(TAG, "Ad failed to show fullscreen content");
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                                Log.d(TAG, "Ad recorded an impression");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                Log.d(TAG, "Ad showed fullscreen content");
                            }
                        });
                    }
                });

        Button interstitialAdButton = findViewById(R.id.interstitialAd);
        interstitialAdButton.setOnClickListener(v->{

        if(myInterstitialAd != null){
            myInterstitialAd.show(MainActivity.this);
        }else{
            Log.d(TAG, "the ad wasnt ready yet");
        }
    });


    }
    private void setupRewardedAd(){

        AdRequest rewardedAdRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                rewardedAdRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, loadAdError.toString());
                myRewardedAd=null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                myRewardedAd = rewardedAd;
                Log.d(TAG, "Add was loaded");
            }
        });

        Button myRewardedAdButton = findViewById(R.id.rewardAd);
        myRewardedAdButton.setOnClickListener(v ->{
            if(myRewardedAd != null){
                myRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Log.d(TAG, "Ad was clicked");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.e(TAG, "Ad failed to show fullscreen content");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d(TAG, "Ad recorded an impression");
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.d(TAG, "Ad showed fullscreen content");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d(TAG, "the ad wasnt ready yet");
                    }
                });

                myRewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        Log.d(TAG, "The user earned the reward: Amount is: " + rewardAmount + " and type is: " + rewardType);
                    }
                });

            } else{
                Log.d(TAG, "The rewarded ad wasnt loaded yet");
            }
        });

    };

}