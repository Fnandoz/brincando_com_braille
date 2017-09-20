package lfernando.braillevirtual;

import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import in.championswimmer.sfg.lib.SimpleFingerGestures;

public class NewActivity extends AppCompatActivity {

    LinearLayout layout;
    int contadorPonto, contadorLetra = 0;

    TextToSpeech tts;
    Vibrator vibrator;
    Letras letras;
    private SimpleFingerGestures sfg;

    //A: Um dedo
    //B: Dois dedos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        layout = (LinearLayout) findViewById(R.id.newActivity);

        sfg = new SimpleFingerGestures();
        sfg.setConsumeTouchEvents(true);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR)
                    tts.setLanguage(Locale.getDefault());
            }
        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        letras = new Letras();

        checaPonto(contadorLetra, contadorPonto);



        sfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onSwipeDown(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onSwipeLeft(int i, long l, double v) {

                if (i==1) {
                    if (contadorPonto  < 4) {
                        contadorPonto++;
                        checaPonto(contadorLetra, contadorPonto);
                    } else {
                        contadorPonto = 5;
                        checaPonto(contadorLetra, contadorPonto);
                    }

                }else if (i==2){
                    if(contadorLetra < 3){
                        contadorLetra++;
                        contadorPonto = 0;
                        checaPonto(contadorLetra, contadorPonto);
                    }else{
                        contadorLetra = 4;
                        contadorPonto = 0;
                        checaPonto(contadorLetra, contadorPonto);
                    }
                }
                return false;
            }

            @Override
            public boolean onSwipeRight(int i, long l, double v) {
                if (i==1) {
                    if (contadorPonto > 0) {
                        contadorPonto--;
                        checaPonto(contadorLetra, contadorPonto);
                    } else {
                        contadorPonto = 0;
                        checaPonto(contadorLetra, contadorPonto);
                    }

                }else if (i==2){
                    if(contadorLetra>0){
                        contadorLetra--;
                        contadorPonto = 0;
                        checaPonto(contadorLetra, contadorPonto);
                    }else{
                        contadorLetra = 0;
                        contadorPonto = 0;
                        checaPonto(contadorLetra, contadorPonto);
                    }
                }
                return false;
            }

            @Override
            public boolean onPinch(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onUnpinch(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onDoubleTap(int i) {
                return false;
            }
        });

        layout.setOnTouchListener(sfg);
    }

    public void checaPonto(int a, int b){
        Toast.makeText(this, ""+a+" "+b, Toast.LENGTH_SHORT).show();
        if (letras.getPontos(a, b) == 1) {
            vibrator.vibrate(400);
            tts.speak("Ponto "+ (b+1) +" preenchido", TextToSpeech.QUEUE_FLUSH, null);
        }else{
            vibrator.vibrate(100);
            tts.speak("Ponto "+ (b+1) +" vazio", TextToSpeech.QUEUE_FLUSH, null);
        }

        if(b==0 || b==4){
            tts.speak("Letra "+letras.getLetras(a), TextToSpeech.QUEUE_ADD, null);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }
}
