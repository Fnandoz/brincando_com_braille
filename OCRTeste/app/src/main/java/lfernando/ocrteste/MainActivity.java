package lfernando.ocrteste;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.mindorks.paracamera.Camera;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private ImageView mImageView;
    private Button disparo;
    private Bitmap mBitmap;
    private TextView mTextView;
    private VisionServiceClient client;
    private TextToSpeech speech;
    private boolean REPRODUZ_AUDIO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (client==null){
            client = new VisionServiceRestClient(getString(R.string.subscription_key));
        }

        Switch mSwitch = (Switch) findViewById(R.id.swAudio);
        assert mSwitch != null;
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                REPRODUZ_AUDIO = status;
            }
        });



        mImageView = (ImageView) findViewById(R.id.imagem);
        mTextView = (TextView) findViewById(R.id.texto);
        disparo = (Button) findViewById(R.id.capturar);

        disparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagem();
            }
        });

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speech.setLanguage(Locale.ROOT);
                }
            }
        });

    }

    protected void capturarImagem(){
        camera = new Camera(this);
        camera.builder()
                .setDirectory("OCR")
                .setName("ocr_"+System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(90)
                .setImageHeight(800);

        camera.takePicture();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Camera.REQUEST_TAKE_PHOTO){
            Bitmap bitmap = camera.getCameraBitmap();
            if(bitmap != null){
                mImageView.setImageBitmap(bitmap);
                mBitmap = bitmap;
                doRecognize();

            }else{
                Toast.makeText(this, "Imagem não capturada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void doRecognize(){
        disparo.setEnabled(false);
        mTextView.setText("Analisando...");
        if(REPRODUZ_AUDIO)
            speech.speak("Analisando imagem.", TextToSpeech.QUEUE_FLUSH, null);
        try {
            new doRequest().execute();
        } catch (Exception e)
        {
            mTextView.setText("Error encountered. Exception is: " + e.toString());
            if(REPRODUZ_AUDIO)
                speech.speak("Erro ao reconhecer texto.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }


    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence

            if (e != null) {
                mTextView.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += word.text + " ";
                        }
                        result += "\n";
                    }
                    result += "\n\n";
                }
                mTextView.setText(result);
                if(REPRODUZ_AUDIO)
                    speech.speak(result, TextToSpeech.QUEUE_FLUSH, null);
            }
            disparo.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
        speech.stop();
        speech.shutdown();
    }
}


