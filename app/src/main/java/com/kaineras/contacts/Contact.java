package com.kaineras.contacts;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created the first version by kaineras on 5/02/15.
 */
public class Contact {

    public final static String ID = "_id";
    public final static String NAME = "name";
    public final static String LASTNAME = "lastname";
    public final static String NICKNAME = "nickname";
    public final static String PICTURE = "picture";

    @DatabaseField(generatedId = true, columnName = ID) private int _id;
    @DatabaseField(columnName = NAME) private String name;
    @DatabaseField(columnName = LASTNAME) private String lastName;
    @DatabaseField(columnName = NICKNAME) private String nickName;
    @DatabaseField(columnName = PICTURE,dataType = DataType.BYTE_ARRAY) private byte[] image;

    public Contact()
    {

    }

    public Contact(String name, String lastName, String nickName, byte[] image) {
        this.setName(name);
        this.setLastName(lastName);
        this.setNickName(nickName);
        this.setImage(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
