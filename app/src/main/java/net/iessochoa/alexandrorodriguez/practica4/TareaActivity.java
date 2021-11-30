package net.iessochoa.alexandrorodriguez.practica4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import model.Tarea;

public class TareaActivity extends AppCompatActivity {
    //Constantes que nos ayudará a pasar los datos a otra actividad
    public final static String EXTRA="net.iessochoa.alexandrorodriguez.practica3.TareaActivity.extra";


    //Atributos
    private Spinner sCategoria;
    private Spinner sPrioridad;
    private Spinner sEstado;

    private TextInputEditText tiet_Tecnico;
    private TextInputEditText tiet_Descripcion;
    private EditText et_Descripcion;

    private FloatingActionButton fab_Guardar;

    private ImageView iv_Estado;
    private ImageView iv_CampoVacio1;
    private ImageView iv_CampoVacio2;
    private ImageView iv_CampoVacio3;

    private String categoria;
    private String prioridad;
    private String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        sCategoria = findViewById(R.id.sCategoria);
        sPrioridad = findViewById(R.id.sPrioridad);
        sEstado = findViewById(R.id.sEstado);
        iv_Estado = findViewById(R.id.iv_Estado);
        fab_Guardar = findViewById(R.id.fab_Guardar);
        tiet_Tecnico = findViewById(R.id.tiet_Tecnico);
        tiet_Descripcion = findViewById(R.id.tiet_Descripcion);
        et_Descripcion = findViewById(R.id.et_Descripcion);

        //ImageView
        iv_CampoVacio1 = findViewById(R.id.iv_CampoVacio1);
        iv_CampoVacio1.setVisibility(View.INVISIBLE);

        iv_CampoVacio2 = findViewById(R.id.iv_CampoVacio2);
        iv_CampoVacio2.setVisibility(View.INVISIBLE);

        iv_CampoVacio3 = findViewById(R.id.iv_CampoVacio3);
        iv_CampoVacio3.setVisibility(View.INVISIBLE);


        //Categoria
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.categoria, android.R.layout.simple_spinner_item);
        sCategoria.setAdapter(adaptador);

        //Prioridad
        adaptador = ArrayAdapter.createFromResource(this, R.array.prioridad, android.R.layout.simple_spinner_item);
        sPrioridad.setAdapter(adaptador);

        //Estado
        adaptador = ArrayAdapter.createFromResource(this, R.array.estado, android.R.layout.simple_spinner_item);
        sEstado.setAdapter(adaptador);

        sEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.estado = (String) parent.getItemAtPosition(position);
                switch (TareaActivity.this.estado) {
                    case "Abierta":
                        iv_Estado.setImageResource(R.drawable.ic_abierto_foreground);
                        break;
                    case "En curso":
                        iv_Estado.setImageResource(R.drawable.ic_encurso_foreground);
                        break;
                    case "Terminada":
                        iv_Estado.setImageResource(R.drawable.ic_terminado_foreground);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sPrioridad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.prioridad = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.categoria = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        fab_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si uno de los campos está vacio
                if (tiet_Tecnico.getText().toString().equalsIgnoreCase("") ||
                        tiet_Descripcion.getText().toString().equalsIgnoreCase("") ||
                        et_Descripcion.getText().toString().equalsIgnoreCase("")) {
                    //Campo Tecnico
                    if (tiet_Tecnico.getText().toString().equalsIgnoreCase("")) {
                        iv_CampoVacio1.setVisibility(View.VISIBLE);
                        Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoTecnico), Toast.LENGTH_SHORT).show();
                    } else {
                        iv_CampoVacio1.setVisibility(View.INVISIBLE);
                    }
                    //Campo Breve Descripción
                    if (tiet_Descripcion.getText().toString().equalsIgnoreCase("")) {
                        iv_CampoVacio2.setVisibility(View.VISIBLE);
                        Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoBreveDescripcion), Toast.LENGTH_SHORT).show();
                    } else {
                        iv_CampoVacio2.setVisibility(View.INVISIBLE);
                    }
                    //Campo Descripción
                    if (et_Descripcion.getText().toString().equalsIgnoreCase("")) {
                        iv_CampoVacio3.setVisibility(View.VISIBLE);
                        Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoDescripcion), Toast.LENGTH_SHORT).show();
                    } else {
                        iv_CampoVacio3.setVisibility(View.INVISIBLE);
                    }
                } else {
                    //Si no estan vacíos
                    pasarDatosAOtraActividad();
                }
            }
        });

        Tarea tarea = getIntent().getParcelableExtra(MainActivity.EXTRA_MAIN);

        if(tarea != null){
            this.setTitle(getResources().getString(R.string.titulo_formateado, tarea.getId()));
            sCategoria.setSelection(obtenerPosicionItem(sCategoria, tarea.getCategoria()));
            sPrioridad.setSelection(obtenerPosicionItem(sPrioridad, tarea.getPrioridad()));
            sEstado.setSelection(obtenerPosicionItem(sEstado, tarea.getEstado()));
            tiet_Tecnico.setText(tarea.getTecnico().toString());
            tiet_Descripcion.setText(tarea.getDescripcion().toString());
            et_Descripcion.setText(tarea.getResumen().toString());
        }



    }

    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String fruta) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    private void pasarDatosAOtraActividad() {

        String tecnico = tiet_Tecnico.getText().toString();
        String descripcion = tiet_Descripcion.getText().toString();
        String resumen = et_Descripcion.getText().toString();


        Tarea tarea = new Tarea(prioridad, categoria, estado, tecnico, descripcion, resumen);
        Intent i = getIntent();



        i.putExtra(EXTRA, tarea);

        setResult(RESULT_OK, i);
        finish();
    }






    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}