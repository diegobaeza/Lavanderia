package cl.baeza.diego.lavanderia.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.DireccionAdapter;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Direccion;


public class UbicacionActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


    private GoogleMap mMap;

    int PLACE_PICKER_REQUEST = 1;

    ImageButton btnAgregar;
    TextView tvSinDireccion;
    Button btnSeleccionarPosicion;
    RecyclerView rvDireccion;

    List<Direccion> direccionList;

    String servicio;
    //String mejora;
    String nombre;
    DireccionAdapter adapter;
    //private MapView mapView;
    ImageView marcadorUbicacion;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ubicacion);



        btnAgregar = findViewById(R.id.btnAgregarD);
        tvSinDireccion = findViewById(R.id.tvSinDireccion);
        btnSeleccionarPosicion = findViewById(R.id.btnSeleccionarPosicion);
        //mapView = (MapView) findViewById(R.id.mapView);

        //mapView.onCreate(savedInstanceState);

        /*mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments



                    }
                });

            }
        });*/


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);


        btnAgregar.setOnClickListener(this);
        btnSeleccionarPosicion.setOnClickListener(this);

        tvSinDireccion.setVisibility(View.INVISIBLE);

        rvDireccion = findViewById(R.id.rvDireccion);
        rvDireccion.setHasFixedSize(true);
        rvDireccion.setLayoutManager(new LinearLayoutManager(this));


        direccionList = loadDirecciones();

        servicio = getIntent().getExtras().getString("servicio");
        //mejora = getIntent().getExtras().getString("mejora");
        nombre = getIntent().getExtras().getString("nombre");

        if(direccionList.isEmpty()){
            tvSinDireccion.setVisibility(View.VISIBLE);
        }else{
            adapter = new DireccionAdapter(this, direccionList, servicio, nombre);

            rvDireccion.setAdapter(adapter);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        direccionList = loadDirecciones();
        if(direccionList.isEmpty()){
            tvSinDireccion.setVisibility(View.VISIBLE);
        }else{
            adapter = new DireccionAdapter(this, direccionList, servicio, nombre);

            rvDireccion.setAdapter(adapter);
        }



        if(getIntent().getExtras().get("direccion") != null){


            String direccionCompleta;
            Direccion direccion = (Direccion) getIntent().getExtras().get("direccion");

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

            Intent i = new Intent(this,HorarioActivity.class);
            i.putExtra("servicio", servicio);
            //i.putExtra("mejora" , mejora);
            i.putExtra("nombre",nombre);
            i.putExtra("ubicacion" , direccionCompleta);
            startActivity(i);
        }
    }



    @Override
    public void onClick(View v) {



        switch (v.getId()){
            case R.id.btnAgregarD:
                Intent i = new Intent(UbicacionActivity.this, AgregarDireccionActivity.class);
                i.putExtra("editando", 0);
                startActivity(i);
                onStop();
                break;
            case R.id.btnSeleccionarPosicion:

                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(this, Locale.getDefault());

                LatLng latlng = mMap.getCameraPosition().target;


                try{
                    addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                }catch (Exception ex){
                    //Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String knownName = addresses.get(0).getFeatureName();

                Direccion direccion =  formatearDireccion(address);
                //Toast.makeText(this,direccion.toString(),Toast.LENGTH_SHORT).show();

                Intent a = new Intent(UbicacionActivity.this, AgregarDireccionActivity.class);
                a.putExtra("direccion", direccion);
                startActivityForResult(a,1);
                onStop();

                break;
        }

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                String direccion = data.getStringExtra("resultado");

                Intent i = new Intent(this,HorarioActivity.class);
                i.putExtra("servicio", servicio);
                //i.putExtra("mejora" , mejora);
                i.putExtra("nombre",nombre);
                i.putExtra("ubicacion" , direccion);
                startActivity(i);
            }
        }
    }


    public ArrayList<Direccion> loadDirecciones() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(UbicacionActivity.this,Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<Direccion> list = new ArrayList<>();

        String[] campos = new String[]{Utilidades.CAMPO_ID, Utilidades.CAMPO_DIRECCION, Utilidades.CAMPO_COMUNA, Utilidades.CAMPO_NRO, Utilidades.CAMPO_DEPTO};
        Cursor c = db.query(Utilidades.TABLA_DIRECCION, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Direccion direccion = new Direccion();
                direccion.setId(c.getInt(0));
                direccion.setDireccion(c.getString(1));
                direccion.setComuna(c.getString(2));
                direccion.setNro_casa(c.getInt(3));
                direccion.setNro_depto(c.getInt(4));
                list.add(direccion);
            }
        } finally {
            c.close();
        }
        db.close();

        return list;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.

            mMap.setMyLocationEnabled(true);
            centrarCamaraMiPosicion();

        }


        //Toast.makeText(this, mMap.getCameraPosition().target+"", Toast.LENGTH_LONG).show();

    }



    public void solicitarPermisos(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    mMap.setMyLocationEnabled(true);
                    centrarCamaraMiPosicion();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void centrarCamaraMiPosicion(){
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng myLocation = new LatLng(latitude,
                longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                18.0f));
    }
    /*private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }*/


    public Direccion formatearDireccion(String direccionGoogle){
        String direccionFormateada = "" ;

        Direccion direccion;
        boolean isCalleFound = false;
        boolean isNumeroFound = false;
        boolean isCityFound = false;

        String calle = "";
        String numero = "";
        String ciudad = "";
        int positionFirsNumber = 0;
        while(!isCalleFound){


            for(int i=0; i < direccionGoogle.length(); i++){
                if(tryParseInt(direccionGoogle.charAt(i)+"") != null){
                    calle = direccionGoogle.substring(0, (i));
                    positionFirsNumber = i;
                    isCalleFound = true;
                    break;
                }
            }
        }

        while(!isNumeroFound){


            for(int i=0; i < direccionGoogle.length(); i++){
                if(tryParseInt(direccionGoogle.charAt(i)+"") != null){
                    numero = direccionGoogle.substring(positionFirsNumber,(i+1));
                    isNumeroFound = true;
                }
            }
        }

        while(!isCityFound){

            int positionFirsAccent = direccionGoogle.indexOf(",");
            int positionSecondAccent = direccionGoogle.indexOf(",", positionFirsAccent+1);


            ciudad = direccionGoogle.substring(positionFirsAccent+1, positionSecondAccent);
            isCityFound = true;
        }
        direccion = new Direccion(calle,ciudad,Integer.parseInt(numero));
        direccionFormateada = "Calle: "+direccion.getDireccion()+" ,Numero: "+direccion.getNro_casa() + " ,Ciudad: "+ direccion.getComuna();

        return direccion;
    }


    public static Integer tryParseInt(String someText) {
        try {
            return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
