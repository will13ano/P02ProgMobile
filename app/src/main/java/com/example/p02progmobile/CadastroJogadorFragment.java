package com.example.p02progmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.p02progmobile.databinding.FragmentCadastroJogadorBinding;
import com.example.p02progmobile.db.DBHelper;
import com.example.p02progmobile.db.Jogador;
import com.example.p02progmobile.db.Time;

import java.util.ArrayList;

public class CadastroJogadorFragment extends Fragment {
    private FragmentCadastroJogadorBinding binding;
    private Jogador novoJogador;
    private ArrayList<Time> arrayListTimes;
    private String selectedTime;

    public CadastroJogadorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCadastroJogadorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Intent it = getActivity().getIntent();

        Jogador jogador = (Jogador) it.getSerializableExtra("jogador");
        novoJogador = new Jogador();

        ListView listTimes = binding.listJogadoresCadastro;
        registerForContextMenu(listTimes);

        DBHelper dbHelper = new DBHelper(getActivity());
        arrayListTimes = dbHelper.selectAllTimes();
        dbHelper.close();

        if (listTimes != null) {
            listTimes.setAdapter(
                    new ArrayAdapter<Time>(getActivity(), android.R.layout.simple_list_item_1, arrayListTimes)
            );
        }

        listTimes.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectedTime = listTimes.getItemAtPosition(i).toString();
        });

        binding.saveJogador.setOnClickListener(view1 -> {
            String nome = binding.jogadorName.getText().toString();
            String cpf = binding.jogadorCpf.getText().toString();
            String anoNascimento = binding.jogadorBirthYear.getText().toString();

            if (nome.equals("") || cpf.equals("") || anoNascimento.equals("") || selectedTime == null) {
                Toast toast = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {

            }
        });
    }

}