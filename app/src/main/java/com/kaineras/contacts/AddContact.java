package com.kaineras.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import java.io.ByteArrayOutputStream;


public class AddContact extends ActionBarActivity {

    ImageButton btCamera;
    Button btSave;
    ImageView ivPicture;
    Bitmap bMap;
    private DatabaseHelper mDBHelper = null;

    public static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        prepareButtons();
    }

    private void prepareButtons() {
        btCamera=(ImageButton)findViewById(R.id.buttonCamera);
        btSave=(Button)findViewById(R.id.buttonSave);

        btCamera.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtName=(TextView)findViewById(R.id.textName);
                TextView txtLast=(TextView)findViewById(R.id.textLast);
                TextView txtNick=(TextView)findViewById(R.id.textNick);

                if(txtName.getText().toString().isEmpty() ||
                   txtLast.getText().toString().isEmpty() ||
                   bMap==null)
                    Toast.makeText(getApplicationContext(),"Fill all the fields",Toast.LENGTH_LONG).show();
                else
                {

                    String name=txtName.getText().toString();
                    String lastname=txtLast.getText().toString();
                    String nickname=txtNick.getText().toString();
                    if(nickname.isEmpty())
                        nickname="_";
                    byte[] image;
                    ByteArrayOutputStream bos;
                    bos = new ByteArrayOutputStream();
                    bMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    image = bos.toByteArray();
                    mDBHelper=getDBHelper();
                    Contact contact=mDBHelper.createContactFromData(name,lastname,nickname,image);
                    mDBHelper.saveContact(contact);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }

    private DatabaseHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return mDBHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            bMap = (Bitmap) data.getExtras().get("data");
            ivPicture = (ImageView) findViewById(R.id.imageContact);
            ivPicture.setImageBitmap(bMap);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
