package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarea implements Parcelable {
    static int contador = 1;
    private int id;
    private String prioridad;
    private String categoria;
    private String estado;
    private String tecnico;
    private String descripcion;
    private String resumen;

    //Construcotr sin ID
    public Tarea(String prioridad, String categoria, String estado, String tecnico, String descripcion, String resumen) {
        this.id = contador++;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.estado = estado;
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.resumen = resumen;
    }

    //Constructor
    public Tarea(int id, String prioridad, String categoria, String estado, String tecnico, String descripcion, String resumen) {
        this.id = id;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.estado = estado;
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.resumen = resumen;
    }

    //Getters and Setters
    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Tarea.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * Creamos el método equals que nos permitirá localizar fácilmente el objeto en un arraylist mediante indexOf
     * Dos notas son iguales si lo es su id
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea nota = (Tarea) o;
        return id == nota.id;
    }

    //Parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.prioridad);
        dest.writeString(this.categoria);
        dest.writeString(this.estado);
        dest.writeString(this.tecnico);
        dest.writeString(this.descripcion);
        dest.writeString(this.resumen);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.prioridad = source.readString();
        this.categoria = source.readString();
        this.estado = source.readString();
        this.tecnico = source.readString();
        this.descripcion = source.readString();
        this.resumen = source.readString();
    }

    protected Tarea(Parcel in) {
        this.id = in.readInt();
        this.prioridad = in.readString();
        this.categoria = in.readString();
        this.estado = in.readString();
        this.tecnico = in.readString();
        this.descripcion = in.readString();
        this.resumen = in.readString();
    }

    public static final Parcelable.Creator<Tarea> CREATOR = new Parcelable.Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel source) {
            return new Tarea(source);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };
}
