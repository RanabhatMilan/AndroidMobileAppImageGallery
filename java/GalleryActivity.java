package com.example.db.imagegallery;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallerylist);

        myDB = new DatabaseHelper(this);

        ListView lv = (ListView) findViewById(R.id.listGallery);
        ArrayList<Person> dolist = myDB.getLi();
        listadapter la = new listadapter(GalleryActivity.this, R.layout.activity_gallery, dolist);
        if (la.isEmpty()){
            Toast.makeText(this,"Your Gallery do not contain any images.Please select and save some images first.",Toast.LENGTH_LONG).show();
            finish();
        }
        lv.setAdapter(la);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(GalleryActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                      //  if (item == 0) {
                       //     Toast.makeText(getApplicationContext(), "Update..", Toast.LENGTH_LONG).show();
                     //   } else {

                           Cursor c = myDB.getData();
                           ArrayList<Integer> arrID = new ArrayList<Integer>();
                           while(c.moveToNext()){
                               arrID.add(c.getInt(0));
                           }
                            showDialogDelete(arrID.get(position));
                      //  }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void showDialogDelete(final int posID){
         AlertDialog.Builder dialog = new AlertDialog.Builder(GalleryActivity.this);
        dialog.setTitle("Warnning!!");
        dialog.setMessage("Are you sure you want to delete this image?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    int abc = myDB.deleteImage(posID);
                    if (abc != 0) {
                        Toast.makeText(GalleryActivity.this, "Image Deleted Successfully.", Toast.LENGTH_LONG).show();
                        startActivity(getIntent());
                        finish();
                    }else {
                        Toast.makeText(GalleryActivity.this, "Image cannot Deleted.", Toast.LENGTH_LONG).show();
                    }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
    
    @Override
            public boolean onOptionsItemSelected(MenuItem item) {
               switch (item.getItemId()) {
                  case android.R.id.home: 
                      onBackPressed();
                      return true;
               }
               return super.onOptionsItemSelected(item);}
            }
    
    }
