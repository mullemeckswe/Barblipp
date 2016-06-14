package barabaraen.barblipp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    public static DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBHelper(this);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        Button btn2 = (Button)findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // db.findKamerer(1);
             // SyncKamererer(db);
              //  SyncGrupper(db);
               // SyncSektioner(db);
               // KryssDialog kd = new KryssDialog(MainActivity.this);
               // kd.show();

                startActivity(new Intent(MainActivity.this, ManKryss.class));
            }
        });

        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KamererKryss.class));
            }
        });
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

    private void SyncKamererer(DBHelper db){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            DBHelper dbh;

            public GetJSON(DBHelper db){
                dbh = db;

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(MainActivity.this, "Added" + dbh.addKamerer(s) + " Kamerer: ",
                        Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(PHPconfig.URL_GET_KAMERERER);
                return s;
            }
        }
        GetJSON gj = new GetJSON(db);
        gj.execute();
    }
    private void SyncGrupper(DBHelper db){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            DBHelper dbh;

            public GetJSON(DBHelper db){
                dbh = db;

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(MainActivity.this, "Added" + dbh.addGrupper(s) + " Grupper: ",
                        Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(PHPconfig.URL_GET_GRUPPER);
                return s;
            }
        }
        GetJSON gj = new GetJSON(db);
        gj.execute();
    }
    private void SyncSektioner(DBHelper db){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            DBHelper dbh;

            public GetJSON(DBHelper db){
                dbh = db;

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(MainActivity.this, "Added" + dbh.addSektioner(s) + " sektioner: ",
                        Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(PHPconfig.URL_GET_SEKTIONER);
                return s;
            }
        }
        GetJSON gj = new GetJSON(db);
        gj.execute();
    }
}
