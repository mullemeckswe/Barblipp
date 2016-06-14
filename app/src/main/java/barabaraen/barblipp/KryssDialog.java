package barabaraen.barblipp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by joakima on 2016-06-13.
 */
public class KryssDialog extends Dialog implements
        android.view.View.OnClickListener,android.view.View.OnLongClickListener {

    public Activity c;
    public Dialog d;
    public Button btn1,btn2,btn3,btn4;
    public TextView nametext;
    protected String name;
    protected int kamererid;
    protected DBHelper db;
    public KryssDialog(Activity a,DBHelper db,String name) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name = name;
        this.kamererid = db.getKamererID(name);
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.kryss_dialog);

        btn1 = (Button) findViewById(R.id.button3);
        btn2 = (Button) findViewById(R.id.button4);
        btn3 = (Button) findViewById(R.id.button5);
        btn4 = (Button) findViewById(R.id.button6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn1.setOnLongClickListener(this);
        btn2.setOnLongClickListener(this);
        btn3.setOnLongClickListener(this);
        btn4.setOnLongClickListener(this);

        nametext = (TextView) findViewById(R.id.NameTag);
        nametext.setText(name);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button3: // avryt
                dismiss();
                break;
            case R.id.button4: //stark
                Toast.makeText(getContext(), "Kryssade Starkkryss på " + name,
                        Toast.LENGTH_SHORT).show();
                db.addKryss(kamererid, 1, 1);

                break;

            case R.id.button5: //mat
                db.addKryss(kamererid, 3, 1);
                Toast.makeText(getContext(), "Kryssade Matkryss på " + name,
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.button6: //svag
                db.addKryss(kamererid, 2, 1);
                Toast.makeText(getContext(), "Kryssade Svagkryss på " + name,
                        Toast.LENGTH_SHORT).show();
                break;


            default:
                break;
        }
    }
    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.button3: // avryt
                dismiss();
                break;
            case R.id.button4: //stark
                Toast.makeText(getContext(), "Tog bort Starkkryss från " + name,
                        Toast.LENGTH_SHORT).show();
                db.addKryss(kamererid, 1, -1);

                break;

            case R.id.button5: //mat
                db.addKryss(kamererid, 3, -1);
                Toast.makeText(getContext(), "Tog bort Matkryss från " + name,
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.button6: //svag
                db.addKryss(kamererid, 2, -1);
                Toast.makeText(getContext(), "Tog bort Svagkryss från " + name,
                        Toast.LENGTH_SHORT).show();
                break;


            default:
                break;
        }
        return true;
    }
}
