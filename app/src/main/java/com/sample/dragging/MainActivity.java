package com.sample.dragging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView list;

    private ItemAdapter _adp;
    private RecyclerView.Adapter _wrappedAdapter;
    private RecyclerViewDragDropManager _dragDropManager;

    private DatabaseHelper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        list=findViewById(R.id.list);

        helper=new DatabaseHelper(this);

        _dragDropManager = new RecyclerViewDragDropManager();
        _adp=new ItemAdapter(ListItem.AllItems(helper),helper);

        _wrappedAdapter = _dragDropManager.createWrappedAdapter(_adp);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(_wrappedAdapter);

        _dragDropManager.attachRecyclerView(list);

    }

}
