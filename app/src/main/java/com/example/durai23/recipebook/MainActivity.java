package com.example.durai23.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*This class supports the main activity which has two buttons.
The buttons open either add recipe activity or search recipe activity.*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "RecipeApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addRecipe = (Button)findViewById(R.id.addRecipe_button);
        Button searchRecipe = (Button)findViewById(R.id.searchRecipe_button);

        //handled onClick method
        addRecipe.setOnClickListener(this);
        searchRecipe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){//checks button IDs
            case R.id.addRecipe_button:
                Intent intentAdd = new Intent(this,AddRecipe.class);
                startActivity(intentAdd);//start Add Recipe activity
                Log.i(TAG,"Add Recipe Acitivity opened.");
                break;
            case R.id.searchRecipe_button:
                Intent intentSearch = new Intent(this,SearchRecipe.class);
                startActivity(intentSearch);//start Search Recipe activity
                Log.i(TAG,"Search Recipe Acitivity opened.");
                break;
            default:
                Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show();
        }
    }
}
