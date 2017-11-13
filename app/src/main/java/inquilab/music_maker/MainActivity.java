package inquilab.music_maker;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AudioTrack track;
    private InputStream[] stream = new InputStream[2];
    private byte[][] music = new byte[2][];
    private void doStuff() {
        stream[0] = getResources().openRawResource(R.raw.guitarc4); // res/raw/click.wav
        int minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        track = new AudioTrack.Builder()
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
        int i = 0;
        music[0] = null;
        try{
            music[0] = new byte[512];
            track.play();
            while((i = stream[0].read(music[0])) != -1)
            {
                track.write(music[0], 0, i);
                Log.d(getClass().getName(), "samples: " + Arrays.toString(music));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            stream[0].close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doStuff2() {
        stream[1] = getResources().openRawResource(R.raw.guitarc3);
        music[1] = null; // feed in increments of 512 bytes
        int i = 0;
        try{
            music[1] = new byte[512];
            track.play();
            while((i = stream[1].read(music[1])) != -1)
            {
                track.write(music[1], 0, i);
                Log.d(getClass().getName(), "samples: " + Arrays.toString(music));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream[1].close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        track.release();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStuff();
                doStuff2();
            }
        });
    }
}
