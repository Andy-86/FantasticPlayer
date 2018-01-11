package com.example.andy.player.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;

import com.example.andy.player.R;
import com.example.andy.player.bean.BaseActivity;

/**
 * Created by andy on 2017/12/29.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar("关于FantasticMusic", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.ll_fragment_container, new AboutFragment()).commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    public static class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private Preference mVersion;
        private Preference mUpdate;
        private Preference mShare;
        private Preference mStar;
        private Preference mWeibo;
        private Preference mJianshu;
        private Preference mGithub;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_about);

            mVersion = findPreference("version");
            mUpdate = findPreference("update");
            mShare = findPreference("share");
            mStar = findPreference("star");
            mJianshu = findPreference("jianshu");
            mGithub = findPreference("github");

            setListener();
        }

        private void setListener() {
            mUpdate.setOnPreferenceClickListener(this);
            mShare.setOnPreferenceClickListener(this);
            mStar.setOnPreferenceClickListener(this);
            mJianshu.setOnPreferenceClickListener(this);
            mGithub.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == mUpdate) {
                return true;
            } else if (preference == mShare) {
                share();
                return true;
            } else if (preference == mStar) {
                openUrl(getString(R.string.about_project_url));
                return true;
            } else if (preference == mWeibo || preference == mJianshu || preference == mGithub) {
                openUrl(preference.getSummary().toString());
                return true;
            }
            return false;
        }

        @SuppressLint("StringFormatInvalid")
        private void share() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app, getString(R.string.app_name)));
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
        }

        private void openUrl(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}