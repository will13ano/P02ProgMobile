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
    private Time selectedTime;
    private Jogador jogador;
    private DBHelper dbHelper;

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
        ListView listTimes = binding.listJogadoresCadastro;
        registerForContextMenu(listTimes);

        dbHelper = new DBHelper(getActivity());
        ArrayList<Time> arrayListTimes = dbHelper.selectAllTimes();

        if (listTimes != null) {
            listTimes.setAdapter(
                    new ArrayAdapter<Time>(getActivity(), android.R.layout.simple_list_item_1, arrayListTimes)
            );
        }

        listTimes.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectedTime = (Time) listTimes.getItemAtPosition(i);
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
                Jogador jogador1 = new Jogador();
                jogador1.setNome(nome);
                jogador1.setAnoNascimento(Integer.parseInt(anoNascimento));
                jogador1.setCpf(cpf);
                jogador1.setIdTime(selectedTime.getIdTime());

                long id = dbHelper.inserirJogador(jogador1);
                Toast toast;

                if ( id != -1.0 ) {
                    toast = Toast.makeText(getActivity(), R.string.created_jogador, Toast.LENGTH_SHORT);
                    reload();
                } else {
                    toast = Toast.makeText(getActivity(), R.string.uncreated_jogador, Toast.LENGTH_SHORT);
                }

                toast.show();
            }
        });
        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle.getBoolean("edit")) {
            jogador = (Jogador) bundle.get("jogador");
            if (jogador != null ) {
                binding.jogadorName.setText(jogador.getNome());
                binding.jogadorCpf.setText(jogador.getCpf());
                binding.jogadorBirthYear.setText(String.valueOf(jogador.getAnoNascimento()));
                selectedTime = dbHelper.getTime(jogador.getIdTime());

                binding.saveJogador.setOnClickListener(view1 -> {
                    jogador.setNome(binding.jogadorName.getText().toString());
                    jogador.setCpf(binding.jogadorCpf.getText().toString());
                    jogador.setAnoNascimento(Integer.parseInt(binding.jogadorBirthYear.getText().toString()));
                    jogador.setIdTime(selectedTime.getIdTime());

                    Toast toast;

                    if ( dbHelper.updateJogador(jogador) ) {
                        toast = Toast.makeText(getActivity(), R.string.updated_jogador, Toast.LENGTH_SHORT);
                        reload();
                    } else {
                        toast = Toast.makeText(getActivity(), R.string.unupdated_jogador, Toast.LENGTH_SHORT);
                    }

                    toast.show();
                });

            }
        }

    }

    public void reload() {
        Intent it = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        getActivity().startActivity(it);
    }

}