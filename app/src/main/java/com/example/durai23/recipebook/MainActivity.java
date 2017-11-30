package com.example.durai23.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addRecipe = (Button)findViewById(R.id.addRecipe_button);
        Button searchRecipe = (Button)findViewById(R.id.searchRecipe_button);

        addRecipe.setOnClickListener(this);
        searchRecipe.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.addRecipe_button:
                Intent intentAdd = new Intent(this,AddRecipe.class);
                startActivity(intentAdd);
                break;
            case R.id.searchRecipe_button:
                Intent intentSearch = new Intent(this,SearchRecipe.class);
                startActivity(intentSearch);
                break;
            default:
                Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show();
        }
    }
}
