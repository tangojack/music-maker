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

    ListView recordings;
    ArrayList<String> lines = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/storage/emulated/0/Downloads/";
        File notes = new File(filePath); //getting the notes dir


        for (File file : notes.listFiles()) { //iterating the files in the dir
            lines.add(file.getName());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lines);
            recordings.setAdapter(adapter);

        }

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(lines.get(position));
            } catch (FileNotFoundException e) {
            e.printStackTrace();
          }


    }


}




