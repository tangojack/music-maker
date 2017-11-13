package inquilab.music_maker;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.health.SystemHealthManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  //grbyhg
    private AudioTrack[] track = new AudioTrack[2];
    private InputStream[] stream = new InputStream[2];
    private byte[][] music = new byte[2][];
    
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
                Log.d(getClass().getName(), "samples: " + Arrays.toString(music));
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
//        try {
//            stream[stream_id].close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        stream[stream_id] = getResources().openRawResource(resource_id);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//        setResource(0, R.raw.guitarc4);
//        setResource(1, R.raw.guitarc3);
        
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doStuff(0, R.raw.guitarc4);
                    }
                };

                Thread t1 = new Thread(r);
                t1.start();
                long futureTime = System.currentTimeMillis() + 500;
                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime - System.currentTimeMillis());

                        }
                        catch(Exception e){
                        }
                    }
                }
                doStuff(1, R.raw.guitarc3);
            }
        });
    }
}
