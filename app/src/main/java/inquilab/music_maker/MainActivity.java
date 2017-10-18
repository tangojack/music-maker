package inquilab.music_maker;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.hurt);
                mediaPlayer.start();
            }
        });


    }
}
