package com.riders.testing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;


import com.riders.testing.model.YoutubeItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by michael on 28/01/2016.
 */
public class YoutubeDatabaseRepository extends Repository<YoutubeItem> {

    private static final String TAG = YoutubeDatabaseRepository.class.getSimpleName();

    private static final String WHERE_ID_EQUALS = YoutubeDatabaseOpenHelper.ID_COLUMN + " =?";

    public YoutubeDatabaseRepository(Context context){
        super();

        dbHelper = new YoutubeDatabaseOpenHelper(context);
    }

    /**
     * Allows to check if the Database is empty
     * @return
     */
    public boolean IsEmpty() {

        boolean ok = false;

        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE, null);
        if (cursor != null) {
            try
            {
                cursor.moveToFirst();                       // Always one row returned.
                if (cursor.getInt (0) == 0) {               // Zero count means empty table.
                    Log.i(TAG, "(YoutubeDatabaseRepository) bdd is empty");
                    ok = true;
                }
                else
                {
                    Log.i(TAG, "(YoutubeDatabaseRepository) bdd is not empty");
                    ok = false;
                }
            }
            catch(CursorIndexOutOfBoundsException e)
            {
                ok = true;
            }

        }

        cursor.close();
        Log.i(TAG, "(YoutubeDatabaseRepository) bdd boolean value : " + String.valueOf(ok));

        return ok;
    }


    /**
     *
     * @param video
     * @return
     */
    public void SaveVideo(YoutubeItem video){

        ContentValues values = new ContentValues();

        values.put(YoutubeDatabaseOpenHelper.FAV_ID_COLUMN, video.getFavId());
        values.put(YoutubeDatabaseOpenHelper.FAV_NAME_COLUMN, video.getName());

        Log.e( "OHOH", values.toString() + " --- " + "database value : " + database.toString());

        database.insert(YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE, null, values);
    }


    /**
     *
     * @param video
     * @return
     */
    public void UpdateVideo(YoutubeItem video) {

        ContentValues values = new ContentValues();

        values.put(YoutubeDatabaseOpenHelper.FAV_ID_COLUMN, video.getFavId());
        values.put(YoutubeDatabaseOpenHelper.FAV_NAME_COLUMN, video.getName());

        long result = database.update(YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{
                        String.valueOf(video.getId())
                });

        Log.d("Update Result:", "=" + result);

        //return result;
    }


    /**
     *
     * @param id
     * @return
     */
    public void DeleteVideo(String id) {
        database.delete(
                YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE,
                YoutubeDatabaseOpenHelper.FAV_ID_COLUMN + "=?",
                new String[]{
                        String.valueOf(id)
                });

        Log.e(TAG, "(DeleteContact) success");
    }



    /**
     *
     * @return
     */
    public List<YoutubeItem> GetAll(){

        // Récupération de la liste des contacts.
        Cursor cursor = database.query(
                YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE,
                new String[]{
                        YoutubeDatabaseOpenHelper.ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_NAME_COLUMN
                },
                null, null, null, null, null);
        Log.i(TAG, "(YoutubeDatabaseRepository) List<YoutubeItem> getAll()");

        return ConvertCursorToListObject(cursor);
    }


    /**
     *
     * @param id
     * @return
     */
    public YoutubeItem GetById(String id){

        Log.i(TAG, "(GetById) ID : " + id);

        Cursor cursor = database.query(
                YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE,
                new String[] {
                        YoutubeDatabaseOpenHelper.ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_NAME_COLUMN
                },
                YoutubeDatabaseOpenHelper.ID_COLUMN + "=?",
                new String[] { String.valueOf(id) }, null, null, null,null);

        Log.i(TAG, "return ConvertCursorToObject(cursor)");

        return ConvertCursorToObject(cursor);
    }


    /**
     *
     * @param id
     * @return
     */
    public YoutubeItem GetObject(String id){
        Cursor cursor = database.query(YoutubeDatabaseOpenHelper.YOUTUBE_FAV_TABLE,
                new String[] {
                        YoutubeDatabaseOpenHelper.ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_ID_COLUMN,
                        YoutubeDatabaseOpenHelper.FAV_NAME_COLUMN
                },
                YoutubeDatabaseOpenHelper.ID_COLUMN + "=?",
                new String[] {
                        String.valueOf(id)
                }, null, null, null,null);
        Log.i(TAG, "YoutubeItem getObject(test 1)");

        if (cursor != null)
            cursor.moveToFirst();

        Log.i(TAG, "YoutubeItem getObject(test 2)");

        Log.i(TAG, "YoutubeItem getObject(test 3)");
        Log.i(TAG, "cursor.getCount() = " + cursor.getCount());
//        Log.i(TAG, Integer.parseInt(cursor.getString(0)) + "");
//        Log.i(TAG, cursor.getString(0));
//        Log.i(TAG, cursor.getString(1));


        YoutubeItem contact = new YoutubeItem(Integer.parseInt(cursor.getString(0)), cursor.getString(0), cursor.getString(1));

        Log.i(TAG, "YoutubeItem getObject(int id)");

        return contact;
    }



    /**
     * Convert a cursor into a contact list
     * @param c
     * @return
     */
    @Override
    public List<YoutubeItem> ConvertCursorToListObject(Cursor c) {

        List<YoutubeItem> contactList = new ArrayList<YoutubeItem>();

        // Si la liste est vide
        if (c.getCount() == 0)
            return contactList;

        // position sur le premeir item
        c.moveToFirst();

        // Pour chaque item
        do {

            YoutubeItem contact = ConvertCursorToObject(c);

            contactList.add(contact);
        } while (c.moveToNext());

        // Fermeture du curseur
        c.close();

		Log.i(TAG, "List<YoutubeItem> ConvertCursorToListObject(Cursor c)");

        return contactList;
    }


    /**
     * Method used by ConvertCursorToObject and ConvertCursorToListObject </br>
     * Allows to convert a cursor to a YoutubeItem object
     * @param c
     * @return
     */
    @Override
    public YoutubeItem ConvertCursorToObject(Cursor c) {

        YoutubeItem video = new YoutubeItem(
                c.getString(YoutubeDatabaseOpenHelper.NUM_COLUMN_FAV_ID),
                c.getString(YoutubeDatabaseOpenHelper.NUM_COLUMN_FAV_NAME));
        video.setId(c.getInt(YoutubeDatabaseOpenHelper.NUM_COLUMN_ID));

		Log.i(TAG, "Contact ConvertCursorToObject(Cursor c)");
        return video;
    }


    /**
     * Convert a cursor to a YoutubeItem object
     * @param c
     * @return
     */
    @Override
    public YoutubeItem ConvertCursorToOneObject(Cursor c) {

        c.moveToFirst();
        YoutubeItem video = ConvertCursorToObject(c);
        c.close();

        return video;
    }

}
