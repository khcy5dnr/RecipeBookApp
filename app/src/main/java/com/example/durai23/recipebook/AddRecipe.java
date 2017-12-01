package com.example.durai23.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipe extends AppCompatActivity {

    EditText recipeName;
    EditText recipeInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //initialize
        recipeName = (EditText)findViewById(R.id.recipeName);
        recipeInstruction = (EditText)findViewById(R.id.recipeInstruction);
    }

    public void addRecipe(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Recipe recipe = new Recipe(recipeName.getText().toString(), recipeInstruction.getText().toString());
        dbHandler.addRecipe(recipe);//adds to database
        Toast.makeText(this,"Recipe added.",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void clearText(View view){
        recipeInstruction.setText("");
    }
}
