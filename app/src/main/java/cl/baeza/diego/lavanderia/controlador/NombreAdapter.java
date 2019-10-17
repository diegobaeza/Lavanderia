package cl.baeza.diego.lavanderia.controlador;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Nombre;
import cl.baeza.diego.lavanderia.vista.AgregarDireccionActivity;
import cl.baeza.diego.lavanderia.vista.AgregarNombreActivity;
import cl.baeza.diego.lavanderia.vista.HorarioActivity;
import cl.baeza.diego.lavanderia.vista.UbicacionActivity;

public class NombreAdapter extends RecyclerView.Adapter<NombreAdapter.NombreViewHolder> {

    private Context mContext;

    private List<Nombre> nombreList;

    private String servicio;
    //private String mejora;


    public NombreAdapter(Context mContext, List<Nombre> nombreList, String servicio, String mejora) {
        this.mContext = mContext;
        this.nombreList = nombreList;
        this.servicio = servicio;
        //this.mejora = mejora;
    }


    @NonNull
    @Override
    public NombreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list, null);

        return new NombreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NombreViewHolder holder, final int i) {

        final Nombre nombre = nombreList.get(i);

        holder.tvNombre.setText(nombre.getNombre());

        holder.ibEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(mContext,"usuarios",null,1);
                SQLiteDatabase db = conn.getWritableDatabase();

                String where = Utilidades.CAMPO_ID + "= ?";
                db.delete(Utilidades.TABLA_NOMBRE, where, new String[]{String.valueOf(nombre.getId())});
                db.close();

                onItemDismiss(i);

            }
        });

        holder.cvItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,UbicacionActivity.class);
                i.putExtra("servicio", servicio);
                //i.putExtra("mejora" , mejora);
                i.putExtra("nombre" , holder.tvNombre.getText().toString());
                mContext.startActivity(i);

            }
        });

        holder.ibEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AgregarNombreActivity.class);
                i.putExtra("editando", 1);
                i.putExtra("nombre", nombre);
                i.putExtra("id", nombre.getId());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nombreList.size();
    }


    class NombreViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombre;
        CardView cvItems;
        ImageButton ibEditar;
        ImageButton ibEliminar;


        private NombreViewHolder(View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            cvItems = itemView.findViewById(R.id.cvItems);
            ibEditar = itemView.findViewById(R.id.ibEditar);
            ibEliminar = itemView.findViewById(R.id.ibEliminar);

        }
    }

    private void onItemDismiss(int position) {
        if(position!=-1 && position < nombreList.size())
        {
            nombreList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

    }
}
