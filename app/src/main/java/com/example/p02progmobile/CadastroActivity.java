package com.example.p02progmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.p02progmobile.databinding.ActivityCadastroBinding;

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
    }
}