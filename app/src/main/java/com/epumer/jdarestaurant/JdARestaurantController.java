package com.epumer.jdarestaurant;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JdARestaurantController extends AppCompatActivity
    implements Menus.MenuListener,
        HacerReserva.HacerReservaListener,
        ListaReservas.ListaReservasListener {

    Toolbar mainToolbar;
    List<Plato> menuList;
    List<Reserva> reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jda_restaurant_controller);

        mainToolbar = findViewById(R.id.mainToolbar);
        menuList = new ArrayList<Plato>();
        reservas = new ArrayList<Reserva>();
        setSupportActionBar(mainToolbar);

        new MenuHilo().execute("https://jdarestaurantapi.firebaseio.com/menu.json");
        FirebaseDatabase.getInstance().getReference().child("reservas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reserva reserva = dataSnapshot.getValue(Reserva.class);
                reservas.add(reserva);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Benvinguda benvinguda = new Benvinguda();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, benvinguda).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                Menus menusFragment = new Menus();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, menusFragment).commit();
                break;
            case R.id.hacerReserva:
                HacerReserva hacerReserva = new HacerReserva();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, hacerReserva).commit();
                break;
            case R.id.verReserva:
                ListaReservas listaReservas = new ListaReservas();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, listaReservas).commit();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public List<Plato> getMenuList() {
        return menuList;
    }

    @Override
    public void hacerReserva(Reserva reserva) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("reservas").push();
        ref.setValue(reserva);

    }

    @Override
    public List<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public void verReserva(Reserva reserva) {
        VerReserva verReserva = VerReserva.newInstance(reserva);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, verReserva).commit();
    }

    public class MenuHilo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            URL url = null;
            BufferedReader br = null;
            String data = "";

            try {
                url = new URL(strings[0]);
                connection = (HttpsURLConnection) url.openConnection();

                br =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ( (linea = br.readLine() ) != null ) {
                    data += linea;
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            String result = "";
            Log.i("HEY-EPUMER","He llegado");

            try {
                JSONObject jsonObj = new JSONObject(data);
                Log.i("HEY-EPUMER",jsonObj.toString());
                JSONArray jsonArr = jsonObj.getJSONArray("entrantes");
                for (int i = 0 ; i < jsonArr.length() ; i++) {
                    JSONObject plato = jsonArr.getJSONObject(i);
                    menuList.add(new Plato( plato.getString("nombre"),
                                            plato.getString("ingredientes"),
                                            (float)plato.getInt("precio")));
                }
                jsonArr = jsonObj.getJSONArray("postres");
                for (int i = 0 ; i < jsonArr.length() ; i++) {
                    JSONObject plato = jsonArr.getJSONObject(i);
                    menuList.add(new Plato( plato.getString("nombre"),
                            plato.getString("ingredientes"),
                            (float)plato.getInt("precio")));
                }
                jsonArr = jsonObj.getJSONArray("principales");
                for (int i = 0 ; i < jsonArr.length() ; i++) {
                    JSONObject plato = jsonArr.getJSONObject(i);
                    menuList.add(new Plato( plato.getString("nombre"),
                            plato.getString("ingredientes"),
                            (float)plato.getInt("precio")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
