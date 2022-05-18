package com.example.p02progmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p02progmobile.databinding.FragmentSecondBinding;
import com.example.p02progmobile.databinding.JogadoresDialogBinding;
import com.example.p02progmobile.db.DBHelper;
import com.example.p02progmobile.db.Jogador;
import com.example.p02progmobile.db.Time;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
    private JogadoresDialogBinding dialogBinding;
    private FragmentSecondBinding binding;
    private ArrayAdapter<Time> timeArrayAdapter;
    private Time time;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        dialogBinding = JogadoresDialogBinding.inflate(inflater, container, false);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listTimes = binding.listTimes;
        registerForContextMenu(listTimes);

        DBHelper dbHelper = new DBHelper(getActivity());
        ArrayList<Time> timesArray = dbHelper.selectAllTimes();
        dbHelper.close();

        timeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, timesArray);
        listTimes.setAdapter(timeArrayAdapter);

        listTimes.setOnItemClickListener((adapterView, view1, i, l) -> {
            Time time = timeArrayAdapter.getItem(i);
            Intent it = new Intent(getActivity(), CadastroActivity.class);
            it.putExtra("edit", true);
            it.putExtra("time", time);
            startActivity(it);
        });

        listTimes.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            time = timeArrayAdapter.getItem(i);
            return false;
        });
    }

    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deleteTeam = menu.add(Menu.NONE, 1, 1, R.string.delete);
        MenuItem showJogadores = menu.add(Menu.NONE, 2, 2, R.string.show_players);

        deleteTeam.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DBHelper dbHelper = new DBHelper(getActivity());
                long id = dbHelper.deleteTime(time);
                Toast toast = null;

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

        showJogadores.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DBHelper dbHelper = new DBHelper(getActivity());
                ArrayList<Jogador> jogadoresPorTime = dbHelper.selectJogadoresPorTime(time);

                AlertDialog.Builder builderList = new AlertDialog.Builder(getActivity());
                builderList.setTitle("Jogadores do " + time.getNome());

                builderList.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ArrayAdapter<Jogador> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, jogadoresPorTime);

                builderList.setAdapter(arrayAdapter, null);

                builderList.show();

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