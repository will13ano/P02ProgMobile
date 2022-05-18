package com.example.p02progmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.p02progmobile.databinding.ActivityCadastroBinding;
import com.example.p02progmobile.db.Time;
import com.google.android.material.tabs.TabLayout;

public class CadastroActivity extends AppCompatActivity {
    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager pager = binding.viewPager;
        pager.setAdapter( new CadastroSectionsPagerAdapter(this, getSupportFragmentManager()) );

        binding.tabs.setupWithViewPager(pager);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle.getBoolean("edit")) {
            Time time = (Time) bundle.get("time");
            TabLayout tabs = binding.tabs;
            if (time != null) {
                tabs.selectTab(tabs.getTabAt(1));
            }
        }


    }
}