package cl.baeza.diego.lavanderia.controlador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Direccion;
import cl.baeza.diego.lavanderia.vista.AgregarDireccionActivity;
import cl.baeza.diego.lavanderia.vista.HorarioActivity;
import cl.baeza.diego.lavanderia.vista.UbicacionActivity;

public class DireccionAdapter extends RecyclerView.Adapter<DireccionAdapter.DireccionViewHolder>{

    //this context we will use to inflate the layout
    private Context mContext;

    //we are storing all the products in a list
    private List<Direccion> direccionList;

    private String servicio;
    private String mejora;
    private String nombre;

    //getting the context and product list with constructor
    public DireccionAdapter(Context mContext, List<Direccion> direccionList, String servicio, String mejora, String nombre) {
        this.servicio = servicio;
        this.mejora = mejora;
        this.mContext = mContext;
        this.direccionList = direccionList;
        this.nombre = nombre;
    }

    @Override
    public DireccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list, null);
        return new DireccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DireccionViewHolder holder, final int position) {
        //getting the product of the specified position

        final Direccion direccion = direccionList.get(position);

        //binding the data with the viewholder views
        String direccionCompleta;

        if(direccion.getNro_depto() > 0){
            direccionCompleta = direccion.getDireccion()
                    + " #" + direccion.getNro_casa()
                    + " Depto. " + direccion.getNro_depto()
                    + ", " + direccion.getComuna();
        }
        else{
           direccionCompleta = direccion.getDireccion()
                                + " #" + direccion.getNro_casa()
                                + ", " + direccion.getComuna();
        }

        holder.tvDireccion.setText(direccionCompleta);



        holder.cvItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,HorarioActivity.class);
                i.putExtra("servicio", servicio);
                i.putExtra("mejora" , mejora);
                i.putExtra("nombre",nombre);
                i.putExtra("ubicacion" , holder.tvDireccion.getText().toString());
                mContext.startActivity(i);

            }
        });

        holder.ibEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(mContext,"usuarios",null,1);
                SQLiteDatabase db = conn.getWritableDatabase();

                String where = Utilidades.CAMPO_ID + "= ?";
                db.delete(Utilidades.TABLA_DIRECCION, where, new String[]{String.valueOf(direccion.getId())});
                db.close();

                onItemDismiss(position);

            }
        });

        holder.ibEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AgregarDireccionActivity.class);
                i.putExtra("editando", 1);
                i.putExtra("direccion", direccion);
                i.putExtra("id", direccion.getId());
                mContext.startActivity(i);
            }
        });

    }




    @Override
    public int getItemCount() {
        return direccionList.size();
    }


    class DireccionViewHolder extends RecyclerView.ViewHolder {

        TextView tvDireccion;
        CardView cvItems;
        ImageButton ibEliminar;
        ImageButton ibEditar;

        private DireccionViewHolder(View itemView) {
            super(itemView);

            tvDireccion = itemView.findViewById(R.id.tvNombre);
            cvItems = itemView.findViewById(R.id.cvItems);
            ibEliminar = itemView.findViewById(R.id.ibEliminar);
            ibEditar = itemView.findViewById(R.id.ibEditar);


        }
    }

    private void onItemDismiss(int position) {
        if(position!=-1 && position<direccionList.size())
        {
            direccionList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

    }
}
