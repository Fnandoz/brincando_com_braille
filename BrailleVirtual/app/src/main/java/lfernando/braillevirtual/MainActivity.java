package lfernando.braillevirtual;

import android.content.Context;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button botoes[] = new Button[12];
    Button next;
    TextView letraTV;
    Vibrator vibrator;
    Letras letras;
    SeekBar pSeek, vSeek;
    TextToSpeech tts;

    int contadorLetra = 0, contadorPontos = 0;
    char letra;
    int[] pontos;

    int PREENCHIDO = 300;
    int VAZIO = 100;
    /**
     * Espaço vazio: 100ms
     * Espaço ocupado: 500ms
     * Espaçamento: 2x 300ms
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
        letras = new Letras();
        pSeek = (SeekBar) findViewById(R.id.preenchidoSeekBar);
        vSeek = (SeekBar) findViewById(R.id.vazioSeekBar);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR)
                    tts.setLanguage(Locale.getDefault());
            }
        });

        botoes[0] = (Button) findViewById(R.id.pontoC11);
        botoes[1] = (Button) findViewById(R.id.pontoC12);
        botoes[2] = (Button) findViewById(R.id.pontoC13);
        botoes[3] = (Button) findViewById(R.id.pontoC14);
        botoes[4] = (Button) findViewById(R.id.pontoC15);
        botoes[5] = (Button) findViewById(R.id.pontoC16);
        next = (Button) findViewById(R.id.pxButton);
        letraTV = (TextView) findViewById(R.id.letraTextView);

        for(int i = 0; i< 6; i++)
            botoes[i].setOnClickListener(listener);

        pSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PREENCHIDO = progress;
                vibrator.cancel();
                vibrator.vibrate(PREENCHIDO);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VAZIO = progress;
                vibrator.cancel();
                vibrator.vibrate(VAZIO);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        letraTV.setText(""+letras.getLetras(contadorLetra));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak("Próxima letra.", TextToSpeech.QUEUE_FLUSH, null);
                if(contadorLetra==4){
                    contadorLetra = 0;
                    contadorPontos = 0;
                    letraTV.setText(""+letras.getLetras(contadorLetra));
                }
                else{
                    contadorLetra++;
                    contadorPontos++;
                    letraTV.setText(""+letras.getLetras(contadorLetra));
                }

            }
        });

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        **/
    }

/**
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            letra = letras.getLetras(contadorLetra);
            pontos = letras.getPontos(contadorPontos);
            switch (v.getId()){
                case R.id.pontoC11:
                    if(pontos[0]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 1, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 1, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;

                case R.id.pontoC12:
                    if(pontos[1]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 2, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 2, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;

                case R.id.pontoC13:
                    if(pontos[2]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 3, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 3, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;

                case R.id.pontoC14:
                    if(pontos[3]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 4, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 4, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;

                case R.id.pontoC15:
                    if(pontos[4]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 5, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 5, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;

                case R.id.pontoC16:
                    if(pontos[5]==1) {
                        vibrator.vibrate(PREENCHIDO);
                        tts.speak("Ponto 6, preenchido", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        vibrator.vibrate(VAZIO);
                        tts.speak("Ponto 6, vazio", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;
            }
        }
    };
 **/
}
