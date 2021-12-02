package adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.alexandrorodriguez.practica4.MainActivity;
import net.iessochoa.alexandrorodriguez.practica4.R;

import java.util.List;

import model.Tarea;
import model.TareasViewModel;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder>{

    // Lista con las tareas que vamos a mostrar;
    private List<Tarea> listaTareas;

    //Atributos para poder borrar o editar una tarea
    private OnItemClickBorrarListener listenerBorrar;
    private OnItemClickEditarListener listenerEditar;


    //cuando se modifique la lista, actualizamos el recyclerview
    public void setListaTareas(List<Tarea> tareas){
        listaTareas=tareas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TareasAdapter.TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Le pasamos el layout que representa cada elemento.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasAdapter.TareaViewHolder holder, int position) {
        //Si la lista de tareas no esta vacía
        if (listaTareas != null) {
            //recuperamos la tarea a mostrar
            final Tarea tarea = listaTareas.get(position);

            //mostramos los datos;
            holder.tv_resumen.setText(tarea.getId()+"-"+tarea.getDescripcion());
            holder.tv_tecnico.setText(tarea.getTecnico());

            //en función del tipo de Tarea, mostramos un icono u otro
            switch (tarea.getEstado()) {
                case "Abierta":
                    holder.iv_estado.setImageResource(R.drawable.ic_abierto_foreground);
                    break;
                case "En curso":
                    holder.iv_estado.setImageResource(R.drawable.ic_encurso_foreground);
                    break;
                case "Terminada":
                    holder.iv_estado.setImageResource(R.drawable.ic_terminado_foreground);
                    break;

            }
            //En el caso de que la propiedad se elija alta, se coloreará el item de un color.
            //Un color definido en la carpeta res Color.
            if(tarea.getPrioridad().equals("Alta")){
                holder.clitem.setBackgroundResource(R.color.verde_lima);
            }else{
                holder.clitem.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public int getItemCount() {
        //Para obtener el tamaño de la lista listaTareas.
        //Si no está vacía retorna el tamaño
        //Si está vacia retorna 0, para que no retorne null
        if (listaTareas!= null)
            return listaTareas.size();
        else
            return 0;
    }

    //Clase para controlar los objetos de los RecyclerView
    public class TareaViewHolder extends RecyclerView.ViewHolder {
        //Atributos
        //TextView
        private TextView tv_resumen;
        private TextView tv_tecnico;
        //ImageView
        private ImageView iv_estado;
        private ImageView iv_borrar;
        private ImageView iv_editar;
        //Constraint layout
        private ConstraintLayout clitem;

        //Constructor
        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            //Establecemos cada atributo con cada componente del item
            //TextView
            tv_resumen = itemView.findViewById(R.id.tv_resumen);
            tv_tecnico = itemView.findViewById(R.id.tv_tecnico);
            //ImageView
            iv_estado = itemView.findViewById(R.id.iv_estado);
            iv_borrar = itemView.findViewById(R.id.iv_borrar);
            iv_editar = itemView.findViewById(R.id.iv_editar);
            //Constraint layout
            clitem = itemView.findViewById(R.id.clitem);

            //Al pulsar en el ImageView borrar
            iv_borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Si el atributo Listenerborrar no es null se ejecuta el if.
                    if (listenerBorrar != null){
                        //si se pulsa al icono borrar, le pasamos la tarea. Podemos saber la posición del item en la lista
                        listenerBorrar.onItemBorrarClick(listaTareas.get( TareaViewHolder.this.getBindingAdapterPosition()));
                    }
                }
            });

            //Al pulsar en el ImageView editar
            iv_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Si el atributo ListenerEditar no es null se ejecuta el if.
                    if (listenerEditar != null){
                        //si se pulsa al icono borrar, le pasamos la nota. Podemos saber la posición del item en la lista
                        listenerEditar.onItemEditarClick(listaTareas.get( TareaViewHolder.this.getBindingAdapterPosition()));
                    }
                }
            });


        }
    }

    //Interfaz para poder ejecutar el ImageView Borrar al darle click
    public interface OnItemClickBorrarListener {
        void onItemBorrarClick(Tarea tarea);
    }
    public void setOnClickBorrarListener(OnItemClickBorrarListener listener) {
        this.listenerBorrar = listener;
    }

    //Interfaz para poder ejecutar el ImageView Editar al darle click
    public interface OnItemClickEditarListener {
        void onItemEditarClick(Tarea tarea);
    }
    public void setOnClickEditarListener(OnItemClickEditarListener listener) {
        this.listenerEditar = listener;
    }
}
