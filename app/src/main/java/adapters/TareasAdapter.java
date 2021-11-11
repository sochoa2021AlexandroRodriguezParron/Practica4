package adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.alexandrorodriguez.practica4.R;

import java.util.List;

import model.Tarea;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder>{

    // Lista con las notas que vamos a mostrar;
    private List<Tarea> listaTareas; //


    //cuando se modifique la lista, actualizamos el recyclerview
    public void setListaTareas(List<Tarea> tareas){
        listaTareas=tareas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TareasAdapter.TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fijaros que le pasamos el layout que representa cada elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasAdapter.TareaViewHolder holder, int position) {
        if (listaTareas != null) {
            //recuperamos la tarea a mostrar
            final Tarea tarea = listaTareas.get(position);
            //mostramos los datos;
            holder.tv_resumen.setText(tarea.getId()+"-"+tarea.getDescripcion());
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

            if(tarea.getPrioridad().equals("Alta")){
                holder.clitem.setBackgroundResource(R.color.verde_lima);
            }else{
                holder.clitem.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (listaTareas!= null)
            return listaTareas.size();
        else
            return 0;
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_resumen;
        private TextView tv_tecnico;
        private ImageView iv_estado;
        private ConstraintLayout clitem;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_resumen = itemView.findViewById(R.id.tv_resumen);
            tv_tecnico = itemView.findViewById(R.id.tv_tecnico);
            iv_estado = itemView.findViewById(R.id.iv_estado);
            clitem = itemView.findViewById(R.id.clitem);

        }
    }
}
