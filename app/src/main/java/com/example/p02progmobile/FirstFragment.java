package com.example.p02progmobile;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p02progmobile.databinding.FragmentFirstBinding;
import com.example.p02progmobile.db.DBHelper;
import com.example.p02progmobile.db.Jogador;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private static String selectedJogador;
    private ArrayList<Jogador> arrayJogadores;
    private ArrayAdapter<Jogador> jogadorArrayAdapter;
    private Jogador jogador;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listJogadores = (ListView) binding.listJogadores;

        registerForContextMenu(listJogadores);

        // PEGANDO OS JOGADORES DO BANCO DE DADOS
        DBHelper dbHelper = new DBHelper(getActivity());
        arrayJogadores = dbHelper.selectAllJogadores();
        dbHelper.close();

        if (listJogadores != null) {
            jogadorArrayAdapter = new ArrayAdapter<Jogador>(getActivity(), android.R.layout.simple_list_item_1, arrayJogadores);
            listJogadores.setAdapter(jogadorArrayAdapter);
        }

        listJogadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Jogador jogador = (Jogador) jogadorArrayAdapter.getItem(i);
                Intent it = new Intent(getActivity(), CadastroActivity.class);
                it.putExtra("edit", true);
                it.putExtra("jogador", jogador);
                startActivity(it);
            }
        });


        listJogadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                jogador = jogadorArrayAdapter.getItem(i);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deleteTeam = menu.add(Menu.NONE,
                1,
                1,
                R.string.delete);

        ((MenuItem) deleteTeam).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DBHelper dbHelper = new DBHelper(getActivity());
                long id = dbHelper.deleteJogador(jogador);

                Toast toast = new Toast(getActivity());
                
                if (id == -1.0) {
                    toast.makeText(getActivity(), R.string.jogador_delete_error, Toast.LENGTH_SHORT);
                }
                else {
                    toast.makeText(getActivity(), R.string.jogador_delete_success, Toast.LENGTH_SHORT);
                }

                toast.show();
                reload();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void reload() {
        Intent it = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        getActivity().startActivity(it);
    }
}