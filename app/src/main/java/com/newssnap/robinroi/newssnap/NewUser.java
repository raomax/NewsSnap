package com.newssnap.robinroi.newssnap;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class NewUser extends ActionBarActivity implements View.OnClickListener {
    SQLiteDatabase myDatabase = SQLiteDatabase.openOrCreateDatabase("",null);
    EditText username = null;
    EditText password = null;
    EditText confirmPassword = null;
    TextView psswdNotSame = null;
    public static String usrname = null;
    public static String psswd = null;
    public static String confirmPsswd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        final Button createUserButton = (Button) findViewById(R.id.button_Create_User);
        createUserButton.setOnClickListener(this);
        psswdNotSame = (TextView) findViewById(R.id.textView_Psswd_Not_Same);
        username = (EditText) findViewById(R.id.editText_Username);
        password = (EditText) findViewById(R.id.editText_Password);
        confirmPassword = (EditText) findViewById(R.id.editText_ConfirmPassword);
        username.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
            case R.id.button_Create_User:
                psswd = password.getText().toString();
                confirmPsswd = confirmPassword.getText().toString();
                if(psswd.equals(confirmPsswd) && psswd != null){
                    Intent intent = new Intent(this, HomePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    psswdNotSame.setText("Passwords do not match");
                }
                break;
            case R.id.editText_Username:
                username.setText("");
                break;

        }
    }
    public void database(){
        myDatabase.execSQL("users(username, passwords);");
    }

}
