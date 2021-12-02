package net.iessochoa.alexandrorodriguez.practica4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


import adapters.TareasAdapter;
import model.Tarea;
import model.TareasViewModel;

public class MainActivity extends AppCompatActivity {

    //Constantes que nos ayudará a pasar los datos a otra actividad
    public final static String EXTRA_MAIN="net.iessochoa.alexandrorodriguez.practica3.MainActivity.extra";

    //Atributos
    private TareasAdapter tareasAdapter;
    private TareasViewModel tareasViewModel;
    private RecyclerView rvLista;
    private FloatingActionButton fabAdd;

    //Método que nos permitirá obtener los datos de la actividad TareaActivity
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Si el usuario pulsa OK en la Activity que hemos llamado
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Recuperamos los dados
                        Intent intent = result.getData();
                        Tarea tarea = intent.getParcelableExtra(TareaActivity.EXTRA);
                        //Los añadimos a la lista de tareas que se encuentra en la clase TareaViewModel.
                        tareasViewModel.addTarea(tarea);

                        tareasViewModel.getTareaList().observe(MainActivity.this, new Observer<List<Tarea>>() {
                            @Override
                            public void onChanged(List<Tarea> tarea) {
                                //Actualizamos el recyclerView si hay cambios en la lista de Notas.
                                tareasAdapter.setListaTareas(tarea);
                            }
                        });
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Establecemos conexión del atributo del codigo, con el componente gráfico
        rvLista = findViewById(R.id.rvLista);
        fabAdd = findViewById(R.id.fabAdd);

        //RecyclerView
        tareasAdapter = new TareasAdapter();
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(tareasAdapter);
        //ViewModel
        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        //Se muestren automáticamente
        tareasViewModel.getTareaList().observe(this, new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> tarea) {
                //Actualizamos el recyclerView si hay cambios en la lista de Notas
                tareasAdapter.setListaTareas(tarea);
            }
        });

        //Le damos Utilidad al FAB
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pasa de esta actividad a la TareaActivity
                Intent i = new Intent(MainActivity.this, TareaActivity.class);
                //Lanzamos el result
                mStartForResult.launch(i);
            }
        });

        //Le damos utilidad a los ImageView Borrar y Editar
        tareasAdapter.setOnClickBorrarListener(new TareasAdapter.OnItemClickBorrarListener() {
            @Override
            public void onItemBorrarClick(Tarea tarea) {
                borrarTarea(tarea);
            }
        });
        tareasAdapter.setOnClickEditarListener(new TareasAdapter.OnItemClickEditarListener() {
            @Override
            public void onItemEditarClick(Tarea tarea) {
                editarTarea(tarea);
            }
        });
    }

    /**
     * Permite editar la tarea
     * @param tarea
     */
    private void editarTarea(Tarea tarea) {
        //Pasa de esta actividad a la TareaActivity
        Intent i = new Intent(MainActivity.this, TareaActivity.class);
        //Le manda el objeto tarea
        i.putExtra(EXTRA_MAIN, tarea);
        //Lanzamos el result
        mStartForResult.launch(i);
    }

    /**
     * Permite borrar la tarea, previamente muestra un dialogo para asegurar al usuario
     * que desea borrarla
     * @param tarea
     */
    private void borrarTarea(final Tarea tarea) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.titulo));//Titulo y mensaje

        dialogo.setMessage(getResources().getString(R.string.mensaje, tarea.getId()));
        //Agregamos botón Ok y su evento
        dialogo.setPositiveButton(android.R.string.yes
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //En caso Positivo Borramos la Tarea
                        tareasViewModel.delTarea(tarea);
                    }
                });
        dialogo.setNegativeButton(android.R.string.no
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //En caso negativo no hacemos nada
                    }
                });
        //Mostramos el dialogo
        dialogo.show();
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
            case R.id.action_order:
                Toast.makeText(MainActivity.this, "Se ha ordenado la lista", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_acercade:
                mostrarDialogo();
                break;
        }

        return true;
    }

    /**
     * Método para Mostrar el dialogo
     */
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