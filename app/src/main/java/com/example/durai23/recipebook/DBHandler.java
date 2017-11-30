package com.example.durai23.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Durai23 on 30/11/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private ContentResolver contentResolver;

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

        String selection = "recipe_Name = \"" + recipeName + "\"";

        Cursor cursor = contentResolver.query(ContentProviderHandler.CONTENT_URI,
                projection, selection, null,
                null);

        Recipe recipe = new Recipe();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe.setID(Integer.parseInt(cursor.getString(0)));
            recipe.set_recipeInstructions(cursor.getString(1));
            recipe.set_recipeInstructions(cursor.getString(2));
            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    public boolean deleteRecipe(String recipeName) {
        boolean result = false;

        String selection = "recipe_Name = \"" + recipeName + "\"";

        int rowsDeleted = contentResolver.delete(ContentProviderHandler.CONTENT_URI,
                selection, null);

        if (rowsDeleted > 0)
            result = true;

        return result;
    }
}
