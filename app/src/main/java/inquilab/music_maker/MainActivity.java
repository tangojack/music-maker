package inquilab.music_maker;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Context;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button play;
    Button record;
    Button save;
    SeekBar bpm;
    TextView bpmtext;
    Spinner minimenu;
    boolean isPlaying = false;
    boolean recorded=true;
    Button a,b,c,d,e,f,g,h;
    Button globalbutton;



    String[] menu={"Recently Played","Most Used","Songs","dummy"};

    private class PlayOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    isPlaying = true;
                    play();
                }
            };
            Thread t1 = new Thread(r);
            t1.start();
        }
    }

    private class PauseOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    isPlaying = false;
                    pause();
                }
            };
            Thread t1 = new Thread(r);
            t1.start();
        }
    }
    private AudioTrack[] track = new AudioTrack[2];
    private InputStream[] stream = new InputStream[2];
    private byte[][] music = new byte[2][];


    private void play(){
        while(isPlaying) {
            //Make these AsyncTasks for reference
            //https://stackoverflow.com/questions/7509232/multiple-audiotracks-single-thread-or-multiple
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    doStuff(0, R.raw.guitarc4);
                }
            };
            Thread t1 = new Thread(r1);
            t1.start();

            long futureTime = System.currentTimeMillis() + 5000;
            while (System.currentTimeMillis() < futureTime) {
                synchronized (this) {
                    try {
                        wait(futureTime - System.currentTimeMillis());

                    } catch (Exception e) {}
                }
            }
            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    doStuff(1, R.raw.guitarc3);
                }
            };
            Thread t2 = new Thread(r2);
            t2.start();
        }
    }
    private void pause(){
        track[0].pause();
        track[0].flush();
        track[1].pause();
        track[1].flush();
    }
    private void doStuff(int stream_id, int resource_id) {
        setResource(stream_id, resource_id);
        music[stream_id] = null; // feed in increments of 512 bytes
        int i = 0;
        try{
            music[stream_id] = new byte[512];

            track[stream_id].play();
            while((i = stream[stream_id].read(music[stream_id])) != -1)
            {
                track[stream_id].write(music[stream_id], 0, i);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream[stream_id].close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setResource(int stream_id, int resource_id){
        stream[stream_id] = getResources().openRawResource(resource_id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        a = (Button) findViewById(R.id.Toggle1);
        b = (Button) findViewById(R.id.Toggle2);
        c = (Button) findViewById(R.id.Toggle3);
        d = (Button) findViewById(R.id.Toggle4);
        e = (Button) findViewById(R.id.Toggle5);
        f = (Button) findViewById(R.id.Toggle6);
        g = (Button) findViewById(R.id.Toggle7);
        h = (Button) findViewById(R.id.Toggle8);


        CharSequence colors[] = new CharSequence[] {"Up", "Down", "Chunk", "Rest"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your strum");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0)
                {
                    globalbutton.setText("Up");

                }

                if(which==1)
                {
                    globalbutton.setText("Down");

                }

                if(which==2)
                {
                    globalbutton.setText("Chunk");

                }

                if(which==3)
                {
                    globalbutton.setText("Rest");

                }

            }
        });




        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=a;
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=b;
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=c;
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=d;
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=e;
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=f;
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=g;
            }
        });

        h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                builder.show();

                globalbutton=h;
            }
        });






        //Making AudioTracks
        int minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        track[0] = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                        .build())
                .setTransferMode(AudioTrack.MODE_STREAM)
                .setBufferSizeInBytes(minBufferSize)
                .build();
        track[1] = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                        .build())
                .setTransferMode(AudioTrack.MODE_STREAM)
                .setBufferSizeInBytes(minBufferSize)
                .build();

        //XML Connections
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bpmtext=(TextView)findViewById(R.id.bpmtext);

        bpmtext.setText("BPM");

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new PlayOnClickListener());

        record=(Button) findViewById(R.id.record);
        record.setOnClickListener(new PauseOnClickListener());

        save=(Button) findViewById(R.id.save);
        save.setText("Save");
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Recording Saved",
                        Toast.LENGTH_SHORT).show();
            }
        });

        bpm =(SeekBar)findViewById(R.id.bpm);
        bpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bpmtext.setText(progress + "BPM");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        minimenu = (Spinner) findViewById(R.id.optionspinner);
        //minimenu.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,menu);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minimenu.setAdapter(aa);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rec:
                Intent myIntent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivityForResult(myIntent, 0);

        }
        return true;

    }

/*
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


            switch (position) {

            case 1:

                break;

            case 2:
                break;

            case 3:
                break;

        }




    }


    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }



*/

}




