package com.example.binumtontine.dao;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


/**
 * Classe permettant de sérialiser et désérialiser des objets
 * @author Valdès
 */
public abstract class Serializer {


    /**
     * Serialisation d'un objet
     * @param filename
     * @param object
     * @param context
     */
    public static void serialize (String filename, Object object, Context context){
        try{
            FileOutputStream file = context.openFileOutput(filename,Context.MODE_PRIVATE);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(file);
                oos.writeObject(object);
                oos.flush();
                oos.close();

            }catch (IOException e){
                //erreur de serialization
                e.printStackTrace();
            }


        }catch (FileNotFoundException e){
            //fichier non trouvé
            e.printStackTrace();

        }
    }
    public static Object deSerialize(String filename, Context context){
        try {
            FileInputStream file = context.openFileInput(filename);
            ObjectInputStream ois;
            try{
                ois = new ObjectInputStream(file);
                try {
                    Object object = ois.readObject();
                    ois.close();
                    return object;
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

            }catch (StreamCorruptedException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }catch (FileNotFoundException e){
            //fichier non trouvé
            e.printStackTrace();
        }
        return null;
    }

}
