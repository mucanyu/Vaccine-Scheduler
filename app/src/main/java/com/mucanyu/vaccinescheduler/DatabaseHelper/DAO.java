package com.sourcey.materiallogindemo.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sourcey.materiallogindemo.AsiTablosuDAO;
import com.sourcey.materiallogindemo.BilgilerDAO;
import com.sourcey.materiallogindemo.Child;

import java.util.ArrayList;
import java.util.List;

// Bu classın amacı veritabanı içerisindeki objelere erişerek obje içerisindeki yapıları doldurmak, silmek, SELECT işlerini yapmak
public class DAO {
    SQLiteDatabase db;
    mySQLite_Layer bdb;

    // Constructor
    public DAO(Context c) {
        bdb = new mySQLite_Layer(c);
    }

    // Database ile işlem yapmaya başlayabilmek için ilk olarak database'e erişimi sağlamak gerekiyor
    public void openDB() {
        db = bdb.getWritableDatabase();
    }

    // Database ile işlemler bittiğinde kapatmak için
    public void closeDB() {
        bdb.close();
    }

    public void createUser(User newUser) {
        // Oluşturacağımız query'e parametre geçirmemizi sağlayan obje
        ContentValues val = new ContentValues();
        val.put("name", newUser.getName());
        val.put("email", newUser.getEmail());
        val.put("tcNo", newUser.getTcNo());
        val.put("password", newUser.getPassword());

        // int id = (int) db.insert(...); yapılarak database'in atadığı ID değerini elde edebiliriz
        db.insert("User", null, val);
    }

    public int validateLogin(String email, String pw) {
        String columns[] = {"id", "email", "password"};
        String selection = "email" + " = ?" + " AND " + "password" + " = ?";
        String[] selectionArgs = {email, pw};

        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        int id = -1;

        if (cursorCount > 0) {
            cursor.moveToFirst();
            // columns[] dizisindeki 0. index yani "id" değerini getir
            id = cursor.getInt(0);
            cursor.close();
            db.close();

            return id;
        } else {
            cursor.close();
            db.close();

            return -1;
        }
    }

    public void deleteUser(User user) {
        int id = user.getId();

        db.delete("User", "id ="+id, null);
    }

    // Tüm kullanıcıların listesini DB'den çekmek
    // Cursor, db için iterator aslında. Pointer gibi. Kaydı gösteriyor. Sona-başa, aşağı-yukarı hareket ettirilebilir
    public List<Child> getChildrenList(int parent_id) {
        List<Child> myList = new ArrayList<Child>();
        String columns[] = {"id", "name", "birthDate", "gender"};
        String selection = "parent_id" + " = ?";
        String[] selectionArgs = {String.valueOf(parent_id)};

        // Burada yaptığımız queryi örneğin numarası 5-10 arası olanları getirmemize yarar
        // Bu cursor bu sonuçların üzerinde hareket edecek
        Cursor c = db.query("Child", columns, selection, selectionArgs, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            // Son elemana gelene kadar
            while(!c.isAfterLast()) {
                // 0. kolonu oku yani "id"
                int id = c.getInt(0);
                String name = c.getString(1);
                String birthDate = c.getString(2);
                String gender = c.getString(3);

                Child child = new Child(id, name, birthDate, gender);
                myList.add(child);
                myList.get(myList.size()-1).setId(id);
                myList.get(myList.size()-1).setBirthDate(birthDate);
                c.moveToNext();
            }
        }

        c.close();
        return  myList;
    }

    public List<BilgilerDAO> getBilgilerList() {
        List<BilgilerDAO> myBilgilerList = new ArrayList<BilgilerDAO>();
        String columns[] = {"Bilgiler_id", "Asi_Ismi", "Aciklama", "Asi_Gunu"};
        String orderByID = "Bilgiler_id";

        Cursor c = db.query("Bilgiler", columns, null, null, null, null, orderByID);
        if (c.getCount() > 0) {
            c.moveToFirst();
            // Son elemana gelene kadar
            while(!c.isAfterLast()) {
                // 0. kolonu oku yani "id"
                int Bilgiler_id = c.getInt(0);
                String Asi_Ismi = c.getString(1);
                String Aciklama = c.getString(2);
                int Asi_Gunu = c.getInt(3);

                BilgilerDAO bilgilerDAO = new BilgilerDAO(Bilgiler_id, Asi_Ismi, Aciklama, Asi_Gunu);
                myBilgilerList.add(bilgilerDAO);

                // TODO: Eğer eksik bilgi gelirse setle burada. ÇALIŞIYORSA SİL.
                c.moveToNext();
            }
        }

        c.close();
        return  myBilgilerList;
    }

    public int addChild(String name, String birthDate, String gender, int parent_id) {
        try {
            ContentValues val = new ContentValues();
            val.put("name", name);
            val.put("birthDate", birthDate);
            val.put("gender", gender);
            val.put("parent_id", parent_id);

            // int id = (int) db.insert(...); yapılarak database'in atadığı ID değerini elde edebiliriz
            return (int) db.insertOrThrow("Child", null, val);

        } catch (SQLiteConstraintException e) {
            Log.w("db", "Hatalı foreign key kullanımı! " + parent_id + "-> "+ e.getMessage());
        }  catch (android.database.SQLException e) {
            Log.w("db", "Veritabanına çocuk eklerken bir şeyler ters gitti! " + parent_id + "-> "+ e.getMessage());
        }
        return  -1;
    }

    public void updateChild(Child child) {
        // Database transactions...
        ContentValues val = new ContentValues();
        int id = child.getId();
        val.put("name", child.getName());
        val.put("birthDate", child.getBirthDate());
        val.put("gender", child.getGender());

        db.update("Child", val, "id="+id, null);
    }

    public void addVaccinesForChild(int child_id) {
        try {
            ContentValues val = new ContentValues();
            for (int i = 1; i < 22; i++) {
                val.put("asiYapildiMi", 0);
                val.put("ChildFK_id", child_id);
                val.put("BilgilerFK_id", i);

                db.insertOrThrow("Asi_Tablosu", null, val);
            }
        } catch (SQLiteConstraintException e) {
            Log.w("db", "addVaccinesForChild SQLiteConstraintException -> " + e.getMessage());
        }  catch (android.database.SQLException e) {
            Log.w("db", "addVaccinesForChild SQLException -> " + e.getMessage());
        }
    }

    public boolean isVaccinated(int child_id, int bilgiler_id) {
        String columns[] = {"AsiTablosu_id", "AsiYapildiMi", "BilgilerFK_id", "ChildFK_id"};
        String selection = "ChildFK_id" + " = ? AND " + "BilgilerFK_id = ?";
        String[] selectionArgs = {String.valueOf(child_id), String.valueOf(bilgiler_id)};

        Cursor cursor = db.query("Asi_Tablosu", columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        int isVaccd;

        cursor.moveToFirst();
        // columns[] dizisindeki 1. index yani "AsiYapildiMi" değerini getir
        isVaccd = cursor.getInt(1);

        cursor.close();
        db.close();

        Log.w("isVacc", "****** isVaccinated? -> " + String.valueOf(isVaccd));
        return isVaccd == 1;
    }

    public void changeSwitchCheckedStatus(int value, int asi_id, int child_id) {
        // Database transactions...
        ContentValues val = new ContentValues();
        val.put("AsiYapildiMi", value);
        String whereClause = "BilgilerFK_id="+asi_id + " AND ChildFK_id="+child_id;

        int count = db.update("Asi_Tablosu", val, whereClause, null);
        Log.w("count", "******* Count: " + String.valueOf(count));
    }
}
