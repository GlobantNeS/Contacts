package com.kaineras.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;


public class UpdateDeleteContact extends ActionBarActivity {


    public static final int TAKE_PICTURE = 1;

    private DatabaseHelper mDBHelper = null;
    Contact contact;
    EditText textName;
    EditText textLastName;
    EditText textNickName;
    ImageView ivPicture;
    ImageButton btCamera;
    Button btUpdate;
    Button btDelete;
    Bitmap bMap;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_contact);
        getDBContact();
        fillContact(contact);
        prepareButtons();
    }

    private void prepareButtons() {
        btCamera=(ImageButton)findViewById(R.id.buttonCamera);
        btUpdate=(Button)findViewById(R.id.buttonUpdate);
        btDelete=(Button)findViewById(R.id.buttonDelete);

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBHelper.deleteContact(contact);
                setResult(RESULT_OK);
                finish();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textName=(EditText) findViewById(R.id.textUpdName);
                textLastName=(EditText) findViewById(R.id.textUpdLast);
                textNickName=(EditText) findViewById(R.id.textUpdNick);


                if(textName.getText().toString().isEmpty() ||
                        textLastName.getText().toString().isEmpty() ||
                        bMap==null)
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_LONG).show();
                else
                {

                    String name=textName.getText().toString();
                    String lastname=textLastName.getText().toString();
                    String nickname=textNickName.getText().toString();
                    if(nickname.isEmpty())
                        nickname="_";
                    byte[] image;
                    ByteArrayOutputStream bos;
                    bos = new ByteArrayOutputStream();
                    bMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    image = bos.toByteArray();
                    mDBHelper=getDBHelper();
                    Contact contact=mDBHelper.createContactFromData(name,lastname,nickname,image);
                    contact.set_id(id);
                    mDBHelper.updateContact(contact);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            bMap = (Bitmap) data.getExtras().get("data");
            ivPicture = (ImageView) findViewById(R.id.imageUpdContact);
            ivPicture.setImageBitmap(bMap);
        }
    }

    private void getDBContact() {
        try {
            id=getIntent().getIntExtra("ID",0);
            mDBHelper=getDBHelper();
            contact=mDBHelper.getContact(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillContact(Contact contact) {
        textName=(EditText) findViewById(R.id.textUpdName);
        textLastName=(EditText) findViewById(R.id.textUpdLast);
        textNickName=(EditText) findViewById(R.id.textUpdNick);
        ivPicture=(ImageView)findViewById(R.id.imageUpdContact);
        textName.setText(contact.getName());
        textLastName.setText(contact.getLastName());
        if(contact.getNickName().equals("_"))
            textNickName.setText("");
        else
            textNickName.setText(contact.getNickName());
        byte[] imaByte=contact.getImage();
        BitmapFactory.Options options = new BitmapFactory.Options();
        bMap = BitmapFactory.decodeByteArray(imaByte, 0, imaByte.length, options);
        ivPicture.setImageBitmap(bMap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_delete_contact, menu);
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

    private DatabaseHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return mDBHelper;
    }
}
