package com.example.phone_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import kotlin.experimental.ExperimentalTypeInference;

public class MainActivity extends AppCompatActivity {

    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeRecyclerView();

        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        mElementViewModel.getAllElements().observe(this, elements -> {
            mAdapter.setElementList(elements);


        });

        FloatingActionButton fab = findViewById(R.id.fabMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdditionPhoneActivity.class);
                startActivity(intent);
            }
        });

        mAdapter = new ElementListAdapter(this);
        mAdapter.setOnItemClickListener(new ElementListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Element element) {

                Intent intent = new Intent(MainActivity.this, AdditionPhoneActivity.class);
                intent.putExtra("ELEMENT_ID", element.getId());
                intent.putExtra("MANUFACTURER", element.getManufacturer());
                intent.putExtra("MODEL", element.getModel());
                intent.putExtra("ANDROID_VERSION", element.getAndroidVersion());
                intent.putExtra("WEBSITE", element.getWebsite());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Element elementToDelete = mAdapter.getElementAtPosition(position);
                mElementViewModel.delete(elementToDelete);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    private void initializeRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ElementListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear) {
            mElementViewModel.deleteAll();
            Toast.makeText(this, getString(R.string.all_delete), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearDatabase() {
        ElementViewModel viewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear Database")
                .setMessage("Are you sure you want to clear the database?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        viewModel.clearDatabase();
                        Toast.makeText(getApplicationContext(), "Database cleared", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}