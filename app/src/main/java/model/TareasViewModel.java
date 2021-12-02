package model;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import model.Tarea;

/**
 * La clase ViewModel nos permite mantener los datos en las reconstruciones. En el onCreate recuperamos el viewmodel
 * si venimos de una reconstrucción o creará uno nuevo si es nueva la app. Tenéis un ejemplo en
 * Los datos que creamos en el viewmodel no se pierden y se mantienen en memoria
 * Los datos de tipo LiveData, nos permiten mantener observadores en el UI(la activity) para
 * detectar cuando hay cambios en los datos.
 * Mantendremos en esta clase el CRUD(altas,bajas y modificaciones) sobre la lista de Notas y al actualizar
 * el LiveData se llamará al observer creado en la activity para mostrar los datos en pantalla
 */
public class TareasViewModel extends AndroidViewModel {
    //Si queremos que la actividad reciba un aviso cuando se modifican los datos, tenemos que crear
    //un LiveData
    private MutableLiveData<List<Tarea>> listaNotasLiveData;
    //Esta lista se mantendrá durante la vida de la Actividad
    private List<Tarea> listaTareas;

    /**
     * Constructor
     * @param application
     */
    public TareasViewModel(@NonNull Application application) {
        super(application);
        //El liveData nos permitirá recibir notificaciones  en la actividad cuando se modifique la lista
        listaNotasLiveData=new MutableLiveData<List<Tarea>>();
        //Creamos unos datos de ejemplo
        crearDatos();
        //Avisamos de la modificación con el LiveData
        listaNotasLiveData.setValue(listaTareas);
    }
    /**
     * Nos permite recuperar el LiveData para asignar el listener al Observador en la activity
     * cuando se modifican los datos
     * @return
     */
    public LiveData<List<Tarea>> getTareaList(){
        return listaNotasLiveData;
    }

    /**
     * Nos permite añadir una Nota a la lista
     * @param tarea
     */
    public void addTarea(Tarea tarea){
        //Añadimos una Tarea a la lista, si existe(mismo id), la sustituimos
        int i=listaTareas.indexOf(tarea);
        if(i<0)
            listaTareas.add(tarea);
        else{
            listaTareas.remove(i);
            listaTareas.add(i,tarea);
        }
        //Avisamos al LiveData para que active el Observer y la actividad muestre los cambios
        listaNotasLiveData.setValue(listaTareas);

    }


    /**
     * Eliminamos la nota por id
     * @param tarea
     */
    public void delTarea(Tarea tarea){
        if(listaTareas.size()>0){
            listaTareas.remove(tarea);
            //Avisamos al LiveData para que active el Observer
            listaNotasLiveData.setValue(listaTareas);
        }
    }
    /**
     * Creamos unos datos de muestra
     */
    private void crearDatos() {
        listaTareas=new ArrayList<Tarea>();
        Tarea tarea=new Tarea("Alta","Mantenimiento","Abierta","Juan " +
                "Perez","Actualización de antivirus","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
                listaTareas.add(tarea);
        tarea=new Tarea("Media","Mantenimiento","Terminada","Maria " +
                "Perez","Actualización de S.O.Linux","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
                listaTareas.add(tarea);
        tarea=new Tarea("Baja","Reparación","En curso","Maria " +
                "Lopez","Sustitución de ratones","Lorem ipsum dolor sit amet, consectetur " +
                "adipiscing elit. Mauris laoreet aliquam sapien, quis mattis diam pretium " +
                "vel. Integer nec tincidunt turpis. Vestibulum interdum accumsan massa, sed " +
                "blandit ex fringilla at. Vivamus non sem vitae nisl viverra pharetra. " +
                "Pellentesque pulvinar vestibulum risus sit amet tempor. Sed blandit arcu " +
                "sed risus interdum fermentum. Integer ornare lorem urna, eget consequat " +
                "ante lacinia et. Phasellus ut diam et diam euismod convallis ");
                listaTareas.add(tarea);
        tarea=new Tarea("Media","Comercial","Abierta","Fele " +
                "Martinez","Presentar presupuesto Web","Lorem ipsum dolor sit amet, " +
                "Consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod convallis ");
                listaTareas.add(tarea);
        tarea=new Tarea("Baja","Comercial","Abierta","Fele Martinez","Presentar " +
                "presupuesto Web","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum" +
                " accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl" +
                " viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod convallis");
                listaTareas.add(tarea);
    }
}