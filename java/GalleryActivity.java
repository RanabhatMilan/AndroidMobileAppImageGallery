package com.example.db.imagegallery;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    private static final int PICK_IMAGE = 100;
    ImageView image_view;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallerylist);
        myDB = new DatabaseHelper(this);

        ListView lv = (ListView) findViewById(R.id.listGallery);
        ArrayList<Person> dolist = myDB.getLi();
        listadapter la = new listadapter(GalleryActivity.this, R.layout.activity_gallery, dolist);
        lv.setAdapter(la);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(GalleryActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        if (item == 0) {
                            Toast.makeText(getApplicationContext(), "Update..", Toast.LENGTH_LONG).show();
                        } else {

                           Cursor c = myDB.getData();
                           ArrayList<Integer> arrID = new ArrayList<Integer>();
                           while(c.moveToNext()){
                               arrID.add(c.getInt(0));
                           }
                            showDialogDelete(arrID.get(position));
                        }
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
                   //String pid = posID.toString()
                     myDB.deleteImage(posID);
                     Toast.makeText(GalleryActivity.this,"Image Deleted Successfully.",Toast.LENGTH_LONG).show();
                        startActivity(getIntent());
                        finish();
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

    }  /*  final Button deleteImg = (Button) findViewById(R.id.button6);
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage();
            }
        });

    }            Bitmap res = myDB.viewAllImages();
            if(res == null){
                showMessage("Notice","No any images present in your gallery.");
                return;
            }
        else
           gallImg.setImageBitmap(res);
    }

    private void showMessage(String notice, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(s);
        builder.setTitle(notice);
        builder.show();
    }
}

    }


 /*   private void deleteImage() {
        if (imgUri != null) {
            String x = getPath(imgUri);
            boolean isInserted = myDB.storeImage(x);
            if (isInserted) {
                Toast.makeText(GalleryActivity.this, "Image deleted from your gallery.", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(GalleryActivity.this, "Image not deleted from your gallery."+x, Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(GalleryActivity.this, "Please select image first.", Toast.LENGTH_LONG).show();
    }
    public String getPath(Uri uri) {
        if (uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

} */