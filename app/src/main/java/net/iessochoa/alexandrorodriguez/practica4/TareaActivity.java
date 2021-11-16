package net.iessochoa.alexandrorodriguez.practica4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TareaActivity extends AppCompatActivity {

    private Spinner sCategoria;
    private Spinner sPrioridad;
    private Spinner sEstado;

    private TextInputEditText tiet_Tecnico;
    private TextInputEditText tiet_Descripcion;

    private EditText et_Descripcion;
    private FloatingActionButton fab_Guardar;
    private ImageView iv_Estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        sCategoria = findViewById(R.id.sCategoria);
        sPrioridad = findViewById(R.id.sPrioridad);
        sEstado = findViewById(R.id.sEstado);
        iv_Estado = findViewById(R.id.iv_Estado);

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

                String ele = (String) parent.getItemAtPosition(position);
                switch(ele){
                    case "Abierta":
                        iv_Estado.setImageResource(R.drawable.ic_abierto_foreground);
                        break;
                    case "En Curso":
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
    }
}