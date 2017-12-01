package com.example.durai23.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Durai23 on 30/11/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private ContentResolver contentResolver;

    private ArrayList<String> recipeNameList = new ArrayList<String>();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    public static final String TABLE_OF_RECIPES = "recipes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_NAME = "recipe_Name";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "recipe_Instructions";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPE_TABLE = "CREATE TABLE " +
                TABLE_OF_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPE_NAME
                + " TEXT," + COLUMN_RECIPE_INSTRUCTIONS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_RECIPES);
        onCreate(db);
    }

    public void addRecipe(Recipe recipe) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipe.get_recipeName());
        values.put(COLUMN_RECIPE_INSTRUCTIONS, recipe.get_recipeInstructions());

        contentResolver.insert(ContentProviderHandler.CONTENT_URI, values);
    }

    public Recipe findRecipe(String recipeName) {
        String[] projection = {COLUMN_ID,
                COLUMN_RECIPE_NAME, COLUMN_RECIPE_INSTRUCTIONS };

        String selection = "recipe_Name LIKE ?";

        Cursor cursor = contentResolver.query(ContentProviderHandler.CONTENT_URI, projection, selection,
                                                new String[]{"%" + recipeName + "%" }, null);

        Recipe recipe = new Recipe();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int i = 0;
            do{
                recipeNameList.add(cursor.getString(1));//gets all the recipe that contains keyword from edit text field

            }while(cursor.moveToNext());

            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    public Recipe findSingleRecipe(String recipeName) {
        String[] projection = {COLUMN_ID,
                COLUMN_RECIPE_NAME, COLUMN_RECIPE_INSTRUCTIONS };

        String selection = "recipe_Name = \"" + recipeName + "\"";

        Cursor cursor = contentResolver.query(ContentProviderHandler.CONTENT_URI,projection, selection, null, null);

        Recipe recipe = new Recipe();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe.setID(Integer.parseInt(cursor.getString(0)));
            recipe.set_recipeName(cursor.getString(1));
            recipe.set_recipeInstructions(cursor.getString(2));
            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    public boolean deleteRecipe(int recipe_ID) {
        boolean result = false;

        String selection = "_id = " + Integer.toString(recipe_ID);

        int rowsDeleted = contentResolver.delete(ContentProviderHandler.CONTENT_URI,
                selection, null);//delete recipe based on ID

        if (rowsDeleted > 0)
            result = true;

        return result;
    }

    public void updateRecipe(Recipe recipe) {

        //gets updated values
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipe.get_recipeName());
        values.put(COLUMN_RECIPE_INSTRUCTIONS, recipe.get_recipeInstructions());

        String selection = "_id = " + Integer.toString(recipe.getID());//updates recipe based on ID

        contentResolver.update(ContentProviderHandler.CONTENT_URI,values,selection,null);
    }

    public ArrayList getRecipeNameList(){
        return recipeNameList;
    }
}
