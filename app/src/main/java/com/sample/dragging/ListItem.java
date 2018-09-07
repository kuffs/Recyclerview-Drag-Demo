package com.sample.dragging;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ListItem {

    private int _id;
    private String _description;
    private int _sort;

    public ListItem(int id, String description, int sort) {
        _id = id;
        _description = description;
        _sort = sort;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public int getSort() {
        return _sort;
    }

    public void setSort(int sort) {
        _sort = sort;
    }

    public void save(DatabaseHelper helper){
        String sql="INSERT OR REPLACE INTO Items (uid,Description,Sort) VALUES (?,?,?)";

        Object[] params = new Object[]{_id,_description,_sort};
        helper.executeQuery(sql,params);

    }

    public static List<ListItem> AllItems(DatabaseHelper helper) {
        List<ListItem> items=new ArrayList<>();

        String sql = "SELECT * FROM Items ORDER BY Sort";

        Cursor c =helper.getDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            int id=c.getInt(c.getColumnIndex("Uid"));
            String text=c.getString(c.getColumnIndex("Description"));
            int sort=c.getInt(c.getColumnIndex("Sort"));

            items.add(new ListItem(id,text,sort));
        }

        if (!c.isClosed()) c.close();

        return items;
    }

}
