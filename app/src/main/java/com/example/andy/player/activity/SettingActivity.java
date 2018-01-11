package com.example.andy.player.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;

import com.example.andy.player.R;
import com.example.andy.player.bean.BaseActivity;
import com.example.andy.player.mvp.paly.Api.PlayService;


public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar("设置", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SettingFragment settingFragment = new SettingFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.ll_fragment_container, settingFragment)
                .commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting;
    }

    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{
        private Preference mSoundEffect;
        private Preference mFilterSize;
        private Preference mFilterTime;


        private ProgressDialog mProgressDialog;

        public void setPlayService(PlayService playService) {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_setting);

            mSoundEffect = findPreference(getString(R.string.setting_key_sound_effect));
            mFilterSize = findPreference(getString(R.string.setting_key_filter_size));
            mFilterTime = findPreference(getString(R.string.setting_key_filter_time));
            mSoundEffect.setOnPreferenceClickListener(this);

        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == mSoundEffect) {
                startEqualizer();
                return true;
            }
            return false;
        }

        private void startEqualizer() {
//
        }


    }
}
