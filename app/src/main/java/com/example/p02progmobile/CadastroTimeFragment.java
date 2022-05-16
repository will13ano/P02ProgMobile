package com.example.p02progmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.p02progmobile.databinding.FragmentCadastroJogadorBinding;
import com.example.p02progmobile.databinding.FragmentCadastroTimeBinding;
import com.example.p02progmobile.db.DBHelper;
import com.example.p02progmobile.db.Jogador;
import com.example.p02progmobile.db.Time;


public class CadastroTimeFragment extends Fragment {
    private FragmentCadastroTimeBinding binding;
    private DBHelper dbHelper = new DBHelper(getActivity());

    public CadastroTimeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCadastroTimeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.save.setOnClickListener(view1 -> {
            String nome = binding.nomeJogadorEditText.getText().toString();

            if (nome.equals("") ) {
                Toast toast = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Time time = new Time();
                time.setNome(nome);

                dbHelper.inserirTime(time);
                Toast toast = Toast.makeText(getActivity(), R.string.created_time, Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}