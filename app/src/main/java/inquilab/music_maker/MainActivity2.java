package inquilab.music_maker;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by anchitsom on 15/11/2017.
 */

public class MainActivity2 extends AppCompatActivity {

    ListView record;
    //ArrayList<String> lines = new ArrayList<>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordings);

        try {
            String filePath = "/storage/emulated/0/Download";

            File dir = new File(filePath);

            File[] list = dir.listFiles();

            String[] theNamesOfFiles = new String[list.length];
            for (int i = 0; i < theNamesOfFiles.length; i++) {
                theNamesOfFiles[i] = list[i].getName();
            }


            ArrayAdapter<String> ab=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, theNamesOfFiles);

        }
        catch(NullPointerException e){

            e.printStackTrace();

        }




    }

    }

  /*  public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(lines.get(position));
            } catch (FileNotFoundException e) {
            e.printStackTrace();
          }


    }
*/






