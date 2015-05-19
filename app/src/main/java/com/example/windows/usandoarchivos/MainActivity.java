package com.example.windows.usandoarchivos;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private EditText txtTexto;
    private Button btnGuardar, btnAbrir, btnGuardarint, btnAbrirint;
    private static final int READ_BLOCK_SIZE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAbrir = (Button)findViewById(R.id.btnAbrir);
        btnGuardarint = (Button)findViewById(R.id.btnGuardarint);
        btnAbrirint = (Button)findViewById(R.id.btnAbrirint);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
        btnGuardarint.setOnClickListener(this);
        btnAbrirint.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardar:
                externaguardar();
                break;
            case R.id.btnAbrir:
                externaabrir();
                break;
            case R.id.btnGuardarint:
                internaguardar();
                break;
            case R.id.btnAbrirint:
                internaabrir();
                break;

        }

    }
    public void externaguardar(){
        File sdCard, directory, file = null;

        try {
            // validamos si se encuentra montada nuestra memoria externa
            if (Environment.getExternalStorageState().equals("mounted")) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();


                    String str = txtTexto.getText().toString();

                    // Clase que permite grabar texto en un archivo
                    FileOutputStream fout = null;
                    try {
                        // instanciamos un onjeto File para crear un nuevo
                        // directorio
                        // la memoria externa
                        directory = new File(sdCard.getAbsolutePath() + "/Arc. Mem. Ext");
                        // se crea el nuevo directorio donde se cerara el
                        // archivo
                        directory.mkdirs();

                        // creamos el archivo en el nuevo directorio creado
                        file = new File(directory, "text.txt");

                        fout = new FileOutputStream(file);

                        // Convierte un stream de caracteres en un stream de
                        // bytes
                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str); // Escribe en el buffer la cadena de texto
                        ows.flush(); // Volca lo que hay en el buffer al archivo
                        ows.close(); // Cierra el archivo de texto

                        Toast.makeText(getBaseContext(), "El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();

                        txtTexto.setText("");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }




            }else{
                Toast.makeText(getBaseContext(), "El almacenamineto externo no se encuentra disponible", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }

    public void externaabrir(){
        File sdCard, directory, file = null;

        try {
            // validamos si se encuentra montada nuestra memoria externa
            if (Environment.getExternalStorageState().equals("mounted")) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();




                    try {

                        //Obtenemos el direcorio donde se encuentra nuestro archivo a leer
                        directory = new File(sdCard.getAbsolutePath() + "/Arc. Mem. Ext");

                        //Creamos un objeto File de nuestro archivo a leer
                        file = new File(directory, "text.txt");

                        //Creamos un objeto de la clase FileInputStream
                        //el cual representa un stream del archivo que vamos a leer
                        FileInputStream fin = new FileInputStream(file);

                        //Creaos un objeto InputStreamReader que nos permitira
                        //leer el stream del archivo abierto
                        InputStreamReader isr = new InputStreamReader(fin);

                        char[] inputBuffer = new char[READ_BLOCK_SIZE];
                        String str = "";

                        // Se lee el archivo de texto mientras no se llegue al
                        // final
                        // de él
                        int charRead;
                        while ((charRead = isr.read(inputBuffer)) > 0) {
                            // Se lee por bloques de 100 caracteres
                            // ya que se desconoce el tamaño del texto
                            // Y se va copiando a una cadena de texto
                            String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                            str += strRead;

                            inputBuffer = new char[READ_BLOCK_SIZE];
                        }

                        // Se muestra el texto leido en la caje de texto
                        txtTexto.setText(str);

                        isr.close();

                        Toast.makeText(getBaseContext(), "El archivo ha sido cargado", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

            }else{
                Toast.makeText(getBaseContext(), "El almacenamineto externo no se encuentra disponible", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }

    public void internaguardar(){

            String str = txtTexto.getText().toString();

            //Clase que permite grabar texto en un archivo
            FileOutputStream fout=null;
            try {
                //Metodo que escribe y abre un archivo con un nombre especifica
                //La constante MODE_WORLD_READABLE indica que este arvhivo lo puede
                //leer cualquier apllicacion
                fout = openFileOutput("archivoTexto.txt", MODE_WORLD_READABLE);

                //Convierte un stream de caracteres en un stream de bytes
                OutputStreamWriter ows = new OutputStreamWriter(fout);
                ows.write(str); //Escribe en el buffer la cadena de texto
                ows.flush(); //Volca lo que hay en el buffer al archivo
                ows.close(); //Cierra el archivo de texto

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Toast.makeText(getBaseContext(),"El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();
            txtTexto.setText("");



    }

    public void internaabrir(){

            try {

                //Se lee el archivo de texto indicado
                FileInputStream fin = openFileInput("archivoTexto.txt");
                InputStreamReader isr = new InputStreamReader(fin);

                char[] inputBuffer = new char[READ_BLOCK_SIZE	];
                String str= "";

                //Se lee el archivo de texto mientras no se llegue al final de él
                int charRead;
                while ((charRead=isr.read(inputBuffer))>0) {
                    //Se lee por bloques de 100 caracteres
                    //ya que se desconoce el tamaño del texto
                    //Y se va copiando a una cadena de texto
                    String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                    str += strRead;

                    inputBuffer = new char [READ_BLOCK_SIZE];
                }

                //Se muestra el texto leido en la caje de texto
                txtTexto.setText(str);

                isr.close();

                Toast.makeText(getBaseContext(),"El archivo ha sido cargado", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }


    }
}
