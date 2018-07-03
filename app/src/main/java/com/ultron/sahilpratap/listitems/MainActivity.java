package com.ultron.sahilpratap.listitems;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<Integer> images = new ArrayList<Integer>();
    CustomAdapter adapter;
    String nm[];
    String desc[];
    Integer img[] = {R.drawable.rabindranath,R.drawable.jk,R.drawable.padma,R.drawable.benjamin,
                      R.drawable.rk,R.drawable.sarojini,R.drawable.william,R.drawable.irfan,
                       R.drawable.paul,R.drawable.iron,R.drawable.smith,R.drawable.ed,R.drawable.swift};

    int itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        nm = getResources().getStringArray(R.array.names);
        desc = getResources().getStringArray(R.array.description);

       for(int i=0;i<img.length;i++){

           names.add(nm[i]);
           description.add(desc[i]);
           images.add(img[i]);
       }


        adapter = new CustomAdapter(MainActivity.this,names,description,images);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add("Edit Name");
        menu.add("Change Profession");
        menu.add("Change Image");
        menu.add("Delete");

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        itemId = info.position;
        String tittle = item.getTitle().toString();
        final Dialog dialog = new Dialog(MainActivity.this);

        switch (tittle){

            case "Edit Name":

                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCanceledOnTouchOutside(false);
                final  EditText e1 = dialog.findViewById(R.id.editText);
                e1.setHint(names.get(itemId));
                final Button b1 = dialog.findViewById(R.id.button);
                dialog.show();
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = e1.getText().toString();
                        names.set(itemId,name);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Name Edited",Toast.LENGTH_LONG).show();

                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Change Profession":

                dialog.setContentView(R.layout.custom_dialog_desc);
                dialog.setCanceledOnTouchOutside(false);
                final  EditText e2 = dialog.findViewById(R.id.editText);
                e2.setHint(description.get(itemId));
                final Button b2 = dialog.findViewById(R.id.button);
                dialog.show();
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String desc = e2.getText().toString();
                        description.set(itemId,desc);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Description changed",Toast.LENGTH_LONG).show();
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Change Image":


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction((Intent.ACTION_GET_CONTENT));
                startActivityForResult(Intent.createChooser(intent,"select  picture"),0);
                adapter.notifyDataSetChanged();
                //      Toast.makeText(this,"Image changed",Toast.LENGTH_LONG).show();
                break;

            case "Delete":
                names.remove(itemId);
                description.remove(itemId);
                images.remove(itemId);
                adapter.notifyDataSetChanged();
                Toast.makeText(this,"Item Deleted",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();

        }


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==0 && resultCode ==RESULT_OK && data!=null)
        {
            // Uri use to contain path of file
            Uri filePath = data.getData();
            try {

                //  getContentResolver use to get any data from mobile
                InputStream input = getContentResolver().openInputStream(filePath);
                Bitmap bmp = BitmapFactory.decodeStream(input);
                CircleImageView img = findViewById(R.id.circleImageView);
                img.setImageBitmap(bmp);

            }catch (Exception e){}

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.item1: {
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.item2: {

                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(intent,420);

       //       Toast.makeText(MainActivity.this, "camera", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.item3: {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
     //         Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.item4: {
               Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
               startActivity(intent);
            //  Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.item5:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                startActivity(intent);



               // Toast.makeText(MainActivity.this,"",Toast.LENGTH_LONG).show();


        }

        //     Toast.makeText(MainActivity.this,"Hello!!",Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}
