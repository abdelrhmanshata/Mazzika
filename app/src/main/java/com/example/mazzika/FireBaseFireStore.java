package com.example.mazzika;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FireBaseFireStore implements IFireBase {
    private CollectionReference db;
    private String TAG = "FireBaseFireStore";
    public static final String USER = "User", SINGER = "com.example.mazzika.Singer", SONG = "Songs";
    private String collection;
    private com.example.mazzika.Person person;

    public FireBaseFireStore(String collection, Person person) {
        this.person = person;
        this.collection = collection;
        db = FirebaseFirestore.getInstance().collection(collection);

    }

    private class personField {
        static final String name = "name",
                country = "country", phone = "phone", profileImageUrl = "profileImageUrl";
    }

    private class userField {
        static final String recently = "recently", favorite = "favorite", playLists = "playLists";
    }

    public FireBaseFireStore(String collection) {
        this.collection = collection;
        db = FirebaseFirestore.getInstance().collection(collection);
    }

    @Override
    public void createNewUser() {
        db.document(person.getPhone()).set(person).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + collection + "added successfully");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + collection);
            }
        });
    }

    @Override
    public void changeName(Name newName) {
        db.document(person.getPhone()).update(personField.name, newName);
    }

    @Override
    public void uploadImage(Uri imageUri) {
    }

    @Override
    public void createPlayList(String name) {
        db.document(person.getPhone()).update(userField.playLists + "." + name, new ArrayList<>());
    }

    @Override
    public void addSongToPlayList(String playListName, String songId) {
        db.document(person.getPhone()).update(userField.playLists + "." + playListName, FieldValue.arrayUnion(SONG + "/" + songId));
    }

    @Override
    public void changePlayListName(String currentName, String newName) {

    }

    @Override
    public void deletePlayList(String name) {

    }

    @Override
    public void addSongToFavorite(String songId) {

    }

    @Override
    public void changeProfileImage(String imageUri) {

    }
}
