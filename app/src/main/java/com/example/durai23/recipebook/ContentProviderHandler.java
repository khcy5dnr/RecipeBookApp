package com.example.durai23.recipebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Durai23 on 30/11/2017.
 */

public class ContentProviderHandler extends ContentProvider{

    private DBHandler dbHandler;
    private static final String AUTHORITY = "com.example.durai23.recipebook.ContentProviderHandler";
    private static final String RECIPE_TABLE = "recipes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + RECIPE_TABLE);

    public static final int RECIPES = 1;
    public static final int RECIPE_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, RECIPE_TABLE, RECIPES);
        sURIMatcher.addURI(AUTHORITY, RECIPE_TABLE + "/#", RECIPE_ID);
    }

     public ContentProviderHandler(){
     }

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DBHandler.TABLE_OF_RECIPES);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case RECIPE_ID:
                queryBuilder.appendWhere(DBHandler.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case RECIPES:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(dbHandler.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case RECIPES:
                id = sqlDB.insert(dbHandler.TABLE_OF_RECIPES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(RECIPE_TABLE + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case RECIPES:
                rowsDeleted = sqlDB.delete(dbHandler.TABLE_OF_RECIPES,
                        selection,
                        selectionArgs);
                break;

            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(dbHandler.TABLE_OF_RECIPES,
                            dbHandler.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(dbHandler.TABLE_OF_RECIPES,
                            dbHandler.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case RECIPES:
                rowsUpdated =
                        sqlDB.update(dbHandler.TABLE_OF_RECIPES,
                                values,
                                selection,
                                selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(dbHandler.TABLE_OF_RECIPES,
                                    values,
                                    dbHandler.COLUMN_ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(dbHandler.TABLE_OF_RECIPES,
                                    values,
                                    dbHandler.COLUMN_ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
