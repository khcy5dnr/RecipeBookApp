package com.example.durai23.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
/*This class supports the activity that will be opened to view the saved recipes.
* The recipe name will be entered into the text box and query will be done instantly.*/
public class SearchRecipe extends AppCompatActivity {

    EditText searchTextBox;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        //initialisation
        searchTextBox = (EditText)findViewById(R.id.searchHint);
        listView = (ListView)findViewById(R.id.listViewRecipe);

        //edit text listener
        searchTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    searchRecipe();// runs search every time the text changes
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    listView.setAdapter(null);
                }
            }
        });

        //list view listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipeName = parent.getItemAtPosition(position).toString();
                searchRecipe(recipeName);// if item is clicked
            }
        });
    }

    public void searchRecipe() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Recipe recipe = dbHandler.findRecipe(searchTextBox.getText().toString());

        //populate listView
        if (recipe != null) {
            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dbHandler.getRecipeNameList()));
        }
        else {
            Toast.makeText(this, "No Match Found",Toast.LENGTH_SHORT).show();
        }
    }

    public void searchRecipe(String recipeName) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Recipe recipe = dbHandler.findSingleRecipe(recipeName);

        //set recipe details to be view
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_ID",recipe.getID());
        bundle.putString("recipe_Name",recipe.get_recipeName());
        bundle.putString("recipe_Instructions",recipe.get_recipeInstructions());
        Intent intent = new Intent(SearchRecipe.this, ViewRecipe.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,1000);//starts view recipe activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            searchRecipe();//update list
        }
    }
}
