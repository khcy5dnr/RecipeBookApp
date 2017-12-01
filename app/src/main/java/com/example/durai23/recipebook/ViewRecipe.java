package com.example.durai23.recipebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewRecipe extends AppCompatActivity {

    EditText recipeName;
    EditText recipeInstruction;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        bundle = getIntent().getExtras();

        recipeName = (EditText)findViewById(R.id.recipeName);
        recipeInstruction = (EditText)findViewById(R.id.recipeInstruction);

        recipeName.setText(bundle.getString("recipe_Name"));
        recipeInstruction.setText(bundle.getString("recipe_Instructions"));

    }

    public void updateRecipe(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int recipe_ID = bundle.getInt("recipe_ID");
        Recipe recipe = new Recipe(recipe_ID, recipeName.getText().toString(), recipeInstruction.getText().toString());
        dbHandler.updateRecipe(recipe);
        Toast.makeText(this, "Recipe Updated",Toast.LENGTH_SHORT).show();
    }

    public void deleteRecipe(View view){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        // create alert box when delete is clicked
        builder.setTitle("Delete Recipe?")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        DBHandler dbHandler = new DBHandler(ViewRecipe.this, null, null, 1);
                        dbHandler.deleteRecipe(bundle.getInt("recipe_ID"));
                        Toast.makeText(ViewRecipe.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //Toast.makeText(ViewRecipe.this, "Recipe not deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
