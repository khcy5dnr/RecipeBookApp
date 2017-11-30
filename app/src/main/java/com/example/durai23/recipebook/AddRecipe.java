package com.example.durai23.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRecipe extends AppCompatActivity {

    EditText recipeName;
    EditText recipeInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeName = (EditText)findViewById(R.id.recipeName);
        recipeInstruction = (EditText)findViewById(R.id.recipeInstruction);
    }

    public void addRecipe(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Recipe recipe = new Recipe(recipeName.getText().toString(), recipeInstruction.getText().toString());
        dbHandler.addRecipe(recipe);
    }
}
