package com.riders.testing.database;

import android.database.Cursor;

import java.util.List;

/**
 * Created by michael on 28/01/2016.
 */
public interface IRepository<T> {

    public boolean IsEmpty();

    public void SaveVideo(T entite);
    public void UpdateVideo(T entite);
    public void DeleteVideo(String id);

    public List<T> GetAll();
    public T GetById(String id);
    public T GetObject(String id);

    public List<T> ConvertCursorToListObject(Cursor c);
    public T ConvertCursorToObject(Cursor c);
    public T ConvertCursorToOneObject(Cursor c);

}
