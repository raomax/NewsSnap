package com.newssnap.robinroi.newssnap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    EditText textInput;
    RatingBar textRating;
    public static String userInputText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textRating = (RatingBar) findViewById(R.id.ratingBarShowRating);
        textInput = (EditText) findViewById(R.id.editText_TextInput);
        final Button button = (Button) findViewById(R.id.button_Check);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Check:
                userInputText = textInput.getText().toString();
                new CheckCredibility().execute();
                textRating.setNumStars((int) (Input.sum/10));
                Log.w("BUTTON_PRESSED",userInputText);

                break;
        }
    }
    private class CheckCredibility extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Input.main(userInputText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
