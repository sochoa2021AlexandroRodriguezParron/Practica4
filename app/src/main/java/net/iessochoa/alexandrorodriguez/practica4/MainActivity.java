package net.iessochoa.alexandrorodriguez.practica4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


import adapters.TareasAdapter;
import model.Tarea;
import model.TareasViewModel;

public class MainActivity extends AppCompatActivity {

    private TareasAdapter tareasAdapter;
    private TareasViewModel tareasViewModel;
    private RecyclerView rvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLista = findViewById(R.id.rvLista);

        //RecyclerView
        tareasAdapter = new TareasAdapter();
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(tareasAdapter);
        //ViewModel
        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        // se muestren automáticamente
        tareasViewModel.getTareaList().observe(this, new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> tarea) {
                //actualizamos el recyclerView si hay cambios en la lista de Notas
                tareasAdapter.setListaTareas(tarea);
            }
        });


    }

    //Insertamos el menu con sus opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Le damos función a los botones del menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(MainActivity.this, "Se ha ordenado la lista", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_del:
                break;
            case R.id.action_acercade:
                mostrarDialogo();
                break;
        }

        return true;
    }

    private void mostrarDialogo() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.ic_about));
        String mensajeDialogo = getResources().getString(R.string.mensaje_dialogo1)
                + "\n" + getResources().getString(R.string.mensaje_dialogo2)
                + "\n" + getResources().getString(R.string.mensaje_dialogo3)
                + "\n" + getResources().getString(R.string.mensaje_dialogo4)
                + "\n" + getResources().getString(R.string.mensaje_dialogo5);
        dialogo.setMessage(mensajeDialogo);

        dialogo.setPositiveButton(getResources().getString(R.string.ok), null);
        dialogo.show();
    }


}