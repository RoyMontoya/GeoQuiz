package com.example.amado.geoquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends ActionBarActivity {
    public static final String TAG = "CheatActivity";
    public static final String EXTRA_ANSWER_IS_TRUE ="com.example.amado.geoquiz.answer_is_true";
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mSdkText;
    private boolean mWasAnswered;
    private TextView mAnswerTextView;
    private static final String DID_CHEAT = "cheater";
    private static final String WAS_ANSWERED= "answered";
    private boolean mDidCheat;
    public static final String EXTRA_ANSWER_SHOWN = "com.example.amado.geoquiz.answer_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);


        mSdkText = (TextView)findViewById(R.id.sdk_text_view);
        String sdk = "API level "+Build.VERSION.SDK_INT;
        mSdkText.setText(sdk);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        CheckSavedState(savedInstanceState);
        setAnswerShownResult(mDidCheat);
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWasAnswered=true;
                CheckAndDisplayAnswer();
                mDidCheat = true;
                setAnswerShownResult(mDidCheat);
            }
        });
    }

    private void CheckSavedState(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            Log.d(TAG, "saved is null");
            mDidCheat= false;
            mWasAnswered= false;
        }else{
            mDidCheat =savedInstanceState.getBoolean(DID_CHEAT);
            if(savedInstanceState.getBoolean(WAS_ANSWERED)==true){
                CheckAndDisplayAnswer();
            }
        }
    }

    private void CheckAndDisplayAnswer() {
        if(mAnswerIsTrue){
            mAnswerTextView.setText(R.string.true_button);
        }else{
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DID_CHEAT, mDidCheat);
        outState.putBoolean(WAS_ANSWERED,mWasAnswered);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cheat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
