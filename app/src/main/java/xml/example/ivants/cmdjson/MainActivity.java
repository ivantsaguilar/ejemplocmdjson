package xml.example.ivants.cmdjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button crear, leer;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crear = (Button)findViewById(R.id.crear);
        leer = (Button)findViewById(R.id.leer);
        texto = (TextView)findViewById(R.id.texto);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createJSON();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readJSON();
            }
        });
    }

    public void createJSON() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject item;

        item = new JSONObject();
        item.put("nombre", "platos");
        item.put("cantidad", 10);
        jsonArray.put(item);

        item = new JSONObject();
        item.put("nombre", "flores");
        item.put("cantidad", 100);
        jsonArray.put(item);

        item = new JSONObject();
        item.put("nombre", "mascotas");
        item.put("cantidad", 1);
        jsonArray.put(item);

        String cadenaJSON = jsonArray.toString();

        try {
            FileOutputStream fos = openFileOutput("items.txt",MODE_PRIVATE);
            fos.write(cadenaJSON.getBytes());
            fos.close();

            texto.setText(jsonArray.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readJSON(){
        try {
            FileInputStream fis = openFileInput("items.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuffer buffer = new StringBuffer();

            while(bis.available() != 0){
                char c = (char) bis.read();
                buffer.append(c);
            }

            bis.close();
            fis.close();

            JSONArray jsonArray = new JSONArray(buffer.toString());

            StringBuffer itemBuffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++){
                String nombre = jsonArray.getJSONObject(i).getString("nombre");
                String cantidad = jsonArray.getJSONObject(i).getString("cantidad");
                itemBuffer.append(nombre + "    |       " + cantidad+ "\n");
                texto.setText(itemBuffer.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
