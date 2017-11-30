package com.example.durai23.recipebook;

/**
 * Created by Durai23 on 30/11/2017.
 */

public class Recipe {
    private int _id;
    private String _recipeName;
    private String _recipeInstructions;

    public Recipe(){
    }

    public Recipe(String r_name, String r_instructions){
        this._recipeName = r_name;
        this._recipeInstructions = r_instructions;
    }

    public Recipe(int id, String r_name, String r_instructions){
        this._id = id;
        this._recipeName = r_name;
        this._recipeInstructions = r_instructions;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void set_recipeName(String r_name) {
        this._recipeName = r_name;
    }

    public String get_recipeName() {
        return this._recipeName;
    }

    public void set_recipeInstructions(String r_instructions) {
        this._recipeInstructions = r_instructions;
    }

    public String get_recipeInstructions() {
        return this._recipeInstructions;
    }
}
