package com.example.p02progmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.p02progmobile.databinding.FragmentSecondBinding;
import com.example.p02progmobile.db.DBHelper;
import com.example.p02progmobile.db.Time;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ArrayList<Time> timesArray;
    private ArrayAdapter<Time> timeArrayAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listTimes = (ListView) binding.listTimes;
        registerForContextMenu(listTimes);

        DBHelper dbHelper = new DBHelper(getActivity());
        timesArray = dbHelper.selectAllTimes();
        dbHelper.close();

        if (listTimes != null) {
            timeArrayAdapter = new ArrayAdapter<Time>(getActivity(), android.R.layout.simple_list_item_1, timesArray);
            listTimes.setAdapter(timeArrayAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}