package com.riders.testing.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.riders.testing.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SpeechToTextActivity extends AppCompatActivity implements RecognitionListener {

    // TAG & Context
    private static final String TAG = SpeechToTextActivity.class.getSimpleName();
    private Context context;
    private static final int RESULT_SPEECH = 1230;

    // Views
    @BindView(R.id.speech_input_textView)
    TextView inputTextView;

    @BindView(R.id.speech_button)
    AppCompatImageButton speechButton;

    @BindView(R.id.recording_textView)
    TextView recordingPlaceholderTextView;

    @BindView(R.id.eq_imageView)
    ImageView eqImageView;

    // Speech
    SpeechRecognizer speech;
    Intent recognizerIntent;
    String message;
    float currentFloatDB = 0;


    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        context = this;
        ButterKnife.bind(this);

        // Check permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Log.d(TAG, "permission granted");

                        // Check if speech to text is available on device


                        // Init Speech To Text Variables
                        initSpeechToText();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Log.d(TAG, "permission denied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        speech.stopListening();
        super.onDestroy();
    }
    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////


    ///////////////////////////////////////////
    //
    //  Butterknife methods
    //
    ///////////////////////////////////////////
    @OnClick(R.id.speech_button)
    public void onSpeechButtonClicked() {
        Log.e(TAG, "onSpeechClicked()");

        if (!inputTextView.getText().toString().isEmpty())
            inputTextView.setText("");

        if (!isRecordingViewVisible()) {
            // Display Recording view
            showRecordingView();
            speechButton.setColorFilter(getResources().getColor(R.color.colorAccent2));

            showEq();
        }


        Log.i(TAG, "start listening ");
        speech.startListening(recognizerIntent);
    }
    ///////////////////////////////////////////
    //
    //  Butterknife methods
    //
    ///////////////////////////////////////////


    ///////////////////////////////////////////
    //
    //  Class methods
    //
    ///////////////////////////////////////////

    /**
     * Init speech to text variables in order to use it
     */
    private void initSpeechToText() {

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }


    public boolean isRecordingViewVisible() {
        boolean isVisible = false;

        if (View.VISIBLE == recordingPlaceholderTextView.getVisibility())
            isVisible = true;
        else if (View.INVISIBLE == recordingPlaceholderTextView.getVisibility())
            isVisible = false;

        return isVisible;
    }


    public void showRecordingView() {
        recordingPlaceholderTextView.setVisibility(View.VISIBLE);
    }


    public void hideRecordingView() {
        recordingPlaceholderTextView.setVisibility(View.INVISIBLE);
    }


    public void showEq() {
        eqImageView.setVisibility(View.VISIBLE);
    }


    public void hideEq() {
        eqImageView.setVisibility(View.INVISIBLE);
    }
    ///////////////////////////////////////////
    //
    //  Class methods
    //
    ///////////////////////////////////////////


    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.e(TAG, "onReadyForSpeech()");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech()");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//        Log.i(TAG, "dB changed " + rmsdB);

        //Check if eqView is visible
        if (eqImageView.getVisibility() == View.VISIBLE) {

            // Variables
            Integer min = -50, toZero = 0, max = 50;
            Random random = new Random();

            // Compare current dB value with the next one in order to set random value (negative/positive)
            if (currentFloatDB > rmsdB) {
                // Random negative
                int negRandom = random.nextInt(toZero - min) + toZero;
                eqImageView.getLayoutParams().height = eqImageView.getLayoutParams().height - negRandom;

            } else if (currentFloatDB < rmsdB) {
                // Random positive
                int posRandom = random.nextInt(max + toZero) + toZero;
                eqImageView.getLayoutParams().height = eqImageView.getLayoutParams().height + posRandom;
            }

            // eqImageView.getLayoutParams().height = eqImageView.getLayoutParams().height + random;
            eqImageView.requestLayout();

            // Store current dB value
            currentFloatDB = rmsdB;

        }
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        Log.e(TAG, "FAILED " + error);

        switch (error) {

            case SpeechRecognizer.ERROR_AUDIO:
                message = getString(R.string.error_audio_error);
                break;

            case SpeechRecognizer.ERROR_CLIENT:
                message = getString(R.string.error_client);
                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = getString(R.string.error_permission);
                break;

            case SpeechRecognizer.ERROR_NETWORK:
                message = getString(R.string.error_network);
                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = getString(R.string.error_timeout);
                break;

            case SpeechRecognizer.ERROR_NO_MATCH:
                message = getString(R.string.error_no_match);
                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = getString(R.string.error_busy);
                break;

            case SpeechRecognizer.ERROR_SERVER:
                message = getString(R.string.error_server);
                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = getString(R.string.error_timeout);
                break;

            default:
                message = getString(R.string.error_understand);
                break;
        }

        Log.e(TAG, message);

        if (isRecordingViewVisible()) {
            // Hide Recording view
            hideRecordingView();
            speechButton.setColorFilter(getResources().getColor(R.color.white));
            hideEq();
        }

        inputTextView.setText(message);
//        return message;
    }


    @Override
    public void onResults(Bundle results) {
        Log.e(TAG, "onResults()");

        String result = "";

        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (String element : matches)
            Log.d(TAG, element + "\n");

        Log.d(TAG, "User said : " + matches.get(0));

        if (isRecordingViewVisible()) {
            // Hide Recording view
            hideRecordingView();
            speechButton.setColorFilter(getResources().getColor(R.color.white));
            hideEq();
        }

        result = matches.get(0);
        inputTextView.setText(result);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(TAG, "onPartialResults()");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(TAG, "onEvent()");
    }
    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
}
