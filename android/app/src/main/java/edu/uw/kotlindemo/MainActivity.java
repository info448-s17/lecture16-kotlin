package edu.uw.kotlindemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "**Main**";

    public static final String EXTRA_MESSAGE = "edu.uw.kotlindemo.message";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View launchButton = findViewById(R.id.btnLaunch);
        launchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Launch button pressed");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Greetings from sunny MainActivity!");
                startActivity(intent);
            }
        });


        //set up an adapter
        ArrayList<String> data = new ArrayList<String>(); //model
        for(int i=1; i<=3; i++){
            data.add(i+ " things to do...");
        }

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, data);
        AdapterView listView = (AdapterView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void showToast(View v){
        Toast.makeText(this, "Would you like some toast?", Toast.LENGTH_SHORT).show();
    }

    public void showAlert(View v){
        WarningDialogFragment.newInstance().show(getSupportFragmentManager(), null);
    }

    public static class WarningDialogFragment extends DialogFragment {

        public static WarningDialogFragment newInstance() {

            Bundle args = new Bundle();
            WarningDialogFragment fragment = new WarningDialogFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Alert!")
                    .setMessage("Danger Will Robinson!"); //note chaining
            builder.setPositiveButton("I see it!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.v(TAG, "You clicked okay! Good times :)");
                }
            });
            builder.setNegativeButton("Noooo...", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.v(TAG, "You clicked cancel! Sad times :(");
                }
            });

            AlertDialog dialog = builder.create();
            return dialog;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true; //we've provided a main_menu!
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_menu_item:
                Log.v(TAG, "Hello!");
                adapter.add(adapter.getCount()+1 + " things to do...");
                return true; //handled
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
