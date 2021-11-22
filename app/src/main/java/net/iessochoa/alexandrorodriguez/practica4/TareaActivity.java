package net.iessochoa.alexandrorodriguez.practica4;

import androidx.appcompat.app.AppCompatActivity;

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

public class TareaActivity extends AppCompatActivity {
    //Constantes que nos ayudará a pasar los datos a otra actividad
    public final static String EXTRA_CATEGORIA="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.categoria";
    public final static String EXTRA_PRIORIDAD="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.prioridad";
    public final static String EXTRA_ESTADO="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.estado";
    public final static String EXTRA_TECNICO="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.tecnico";
    public final static String EXTRA_RESUMEN="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.resumen";
    public final static String EXTRA_DESCRIPCION="net.iessochoa.alexandrorodriguez.practica3.NuevoContactoActivity.descripcion";

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
        ArrayAdapter<CharSequence> adaptador =ArrayAdapter.createFromResource(this, R.array.categoria, android.R.layout.simple_spinner_item);
        sCategoria.setAdapter(adaptador);

        //Prioridad
        adaptador =ArrayAdapter.createFromResource(this, R.array.prioridad, android.R.layout.simple_spinner_item);
        sPrioridad.setAdapter(adaptador);

        //Estado
        adaptador =ArrayAdapter.createFromResource(this, R.array.estado, android.R.layout.simple_spinner_item);
        sEstado.setAdapter(adaptador);

        sEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.estado = (String) parent.getItemAtPosition(position);
                switch(TareaActivity.this.estado){
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        sPrioridad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.prioridad = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        sCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TareaActivity.this.categoria = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        fab_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tiet_Tecnico.getText().toString().equalsIgnoreCase("")){
                    iv_CampoVacio1.setVisibility(View.VISIBLE);
                    Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoTecnico), Toast.LENGTH_SHORT).show();
                }else{
                    iv_CampoVacio1.setVisibility(View.INVISIBLE);
                    pasarDatosAOtraActividad();
                }

                if(tiet_Descripcion.getText().toString().equalsIgnoreCase("")){
                    iv_CampoVacio2.setVisibility(View.VISIBLE);
                    Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoBreveDescripcion), Toast.LENGTH_SHORT).show();
                }else{
                    iv_CampoVacio2.setVisibility(View.INVISIBLE);
                    pasarDatosAOtraActividad();
                }

                if(et_Descripcion.getText().toString().equalsIgnoreCase("")){
                    iv_CampoVacio3.setVisibility(View.VISIBLE);
                    Toast.makeText(TareaActivity.this, getResources().getString(R.string.campoDescripcion), Toast.LENGTH_SHORT).show();
                }else{
                    iv_CampoVacio3.setVisibility(View.INVISIBLE);
                    pasarDatosAOtraActividad();
                }

            }
        });
    }

    private void pasarDatosAOtraActividad() {

        String tecnico = tiet_Tecnico.getText().toString();
        String resumen = tiet_Descripcion.getText().toString();
        String descripcion = et_Descripcion.getText().toString();

        Intent i = getIntent();
        i.putExtra(EXTRA_CATEGORIA, TareaActivity.this.categoria);
        i.putExtra(EXTRA_PRIORIDAD, TareaActivity.this.prioridad);
        i.putExtra(EXTRA_ESTADO, TareaActivity.this.estado);
        i.putExtra(EXTRA_TECNICO, tecnico);
        i.putExtra(EXTRA_RESUMEN, resumen);
        i.putExtra(EXTRA_DESCRIPCION, descripcion);

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