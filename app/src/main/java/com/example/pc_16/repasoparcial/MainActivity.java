package com.example.pc_16.repasoparcial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ProgressBar cargador;
    Button boton;
    RecyclerView recyclerView;
    List<Post> myPost;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargador = (ProgressBar) findViewById(R.id.cargador);
        boton = (Button) findViewById(R.id.boton);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        //permite manejar los componentes en un RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //Establecer la orientacion en vertical
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //Asignar a orientaion a mi Recyclerview
        recyclerView.setLayoutManager(linearLayoutManager);
    }


//MEtodo para validar si tenemos internet


    public Boolean isOnLine() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    //Metodo para crear una Tarea

    private class MyTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            cargador.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = null;
            try {
                content = HttpManager.getData(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }


        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                myPost = JSon.getData(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
            cargarDatos();
            cargador.setVisibility(View.GONE);
        }

        public void cargarDatos() {

            // Crear un objeto de tipo "PostAdapter" y retorna el item de mi layout (item.xml)
            adapter = new PostAdapter(getApplicationContext(), myPost);
            // inyectar el item en mi RecyclerView
            recyclerView.setAdapter(adapter);

        }

    }
        public void onClickButton(View view) {
            if (isOnLine()) {
                MyTask task = new MyTask();
                task.execute("https://jsonplaceholder.typicode.com/posts");
            } else {
                Toast.makeText(this, "Sin conexión", Toast.LENGTH_SHORT).show();
            }

        }



}

