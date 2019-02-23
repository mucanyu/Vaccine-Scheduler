package com.sourcey.materiallogindemo.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mySQLite_Layer extends SQLiteOpenHelper {

    public mySQLite_Layer(Context c) {
        super(c, "asiTakibiDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("PRAGMA foreign_keys=ON");
        String userTableSQL = "CREATE TABLE User ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                ", name TEXT NOT NULL, email TEXT NOT NULL, tcNo TEXT NOT NULL, password TEXT NOT NULL )";

        String childTableSQL = "CREATE TABLE Child ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
        ",name TEXT NOT NULL, birthDate TEXT NOT NULL, gender TEXT NOT NULL, parent_id INTEGER NOT NULL " +
                ", FOREIGN KEY(parent_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE )";

        String bilgilerTableSQL = "CREATE TABLE Bilgiler ( Bilgiler_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                ", Asi_Ismi TEXT, Aciklama TEXT, Asi_Gunu INTEGER )";

        String asiTableSQL = "CREATE TABLE Asi_Tablosu ( AsiTablosu_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                ", AsiYapildiMi INTEGER, BilgilerFK_id INTEGER NOT NULL, ChildFK_id INTEGER NOT NULL" +
                ", FOREIGN KEY(BilgilerFK_id) REFERENCES Bilgiler(Bilgiler_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ", FOREIGN KEY(ChildFK_id) REFERENCES Child(id) ON DELETE CASCADE ON UPDATE CASCADE )";

        String Hepatit_B_1 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Hepatit B - 1'," +
                "'0, 1 ve 6. ay çizelgesiyle 3 kez uygulanır. İlk aşı, bebeğin doğduğu gün ya da doğum sonrasındaki birkaç gün içinde yapılmalıdır.\n" +
                "İkinci Hepatit B aşısı birinciden en az bir ay, üçüncü aşı ilkinden en az 4 ay, ikincisinden en az 2 ay sonra yapılmalıdır. Üçüncü (son) aşı, altıncı ay bitmeden yapılmamalıdır. Üçüncü BHA 6-9. ay arasında yapılabilir. Hepatit B aşısı ile bağışıklamaya bütün yenidoğanlarda en geç ilk 72 saat (mümkünse ilk 24 saat) içinde başlanmalıdır.'," +
                "0" +
                ");";

        String Hepatit_B_2 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Hepatit B - 2'," +
                "'0, 1 ve 6. ay çizelgesiyle 3 kez uygulanır. İlk aşı, bebeğin doğduğu gün ya da doğum sonrasındaki birkaç gün içinde yapılmalıdır.\n" +
                "İkinci Hepatit B aşısı birinciden en az bir ay, üçüncü aşı ilkinden en az 4 ay, ikincisinden en az 2 ay sonra yapılmalıdır. Üçüncü (son) aşı, altıncı ay bitmeden yapılmamalıdır. Üçüncü BHA 6-9. ay arasında yapılabilir. Hepatit B aşısı ile bağışıklamaya bütün yenidoğanlarda en geç ilk 72 saat (mümkünse ilk 24 saat) içinde başlanmalıdır.'," +
                "30" +
                ");";

        String Hepatit_B_3 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Hepatit B - 3'," +
                "'0, 1 ve 6. ay çizelgesiyle 3 kez uygulanır. İlk aşı, bebeğin doğduğu gün ya da doğum sonrasındaki birkaç gün içinde yapılmalıdır.\n" +
                "İkinci Hepatit B aşısı birinciden en az bir ay, üçüncü aşı ilkinden en az 4 ay, ikincisinden en az 2 ay sonra yapılmalıdır. Üçüncü (son) aşı, altıncı ay bitmeden yapılmamalıdır. Üçüncü BHA 6-9. ay arasında yapılabilir. Hepatit B aşısı ile bağışıklamaya bütün yenidoğanlarda en geç ilk 72 saat (mümkünse ilk 24 saat) içinde başlanmalıdır.'," +
                "180" +
                ");";

        String BCG = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'BCG (Verem)'," +
                "'Genellikle 2. ayda olmak üzere, 0-3 ay arasında yapılabilir. İlk 3 ayda PPD deri testi yapılmadan, daha sonra PPD deri testi yapılarak ve test negatif bulunursa yapılır.\n" +
                "PPD deri testi pozitifse bebek; ayrıntılı öykü, aile öyküsü, fizik ve laboratuvar inceleme bulguları temelinde verem enfeksiyonu ve verem hastalığı açısından değerlendirilir. Hiçbir hastalık belirtisi, bulgusu yoksa tek ilaçla (izoniyazit) verem enfeksiyonu tedavisi ya da herhangi bir hastalık bulgusu varsa çok ilaçla verem hastalığı tedavisi başlanır, ayrıca aile taraması yapılır.'," +
                "60" +
                ");";

        String Besli_Karma_1 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Beşli Karma - 1'," +
                "'Diğer adıyla DaBT-İPA- Hib (Difteri, Aselüler Boğmaca, Tetanos, İnaktif Polio, Hemofilus Influenza tip B aşısı.\n" +
                "Bu 5 hastalığa karşı geliştirilen karma aşı, 2 ay arayla (2, 4 ve 6. aylarda) 3 kez temel aşılama dizisi, 18-24. ayda 4. aşı (1. pekiştirme) olarak toplam 4 kez uygulanır'," +
                "60" +
                ");";

        String Besli_Karma_2 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Beşli Karma - 2'," +
                "'Diğer adıyla DaBT-İPA- Hib (Difteri, Aselüler Boğmaca, Tetanos, İnaktif Polio, Hemofilus Influenza tip B aşısı.\n" +
                "Bu 5 hastalığa karşı geliştirilen karma aşı, 2 ay arayla (2, 4 ve 6. aylarda) 3 kez temel aşılama dizisi, 18-24. ayda 4. aşı (1. pekiştirme) olarak toplam 4 kez uygulanır'," +
                "120" +
                ");";

        String Besli_Karma_3 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Beşli Karma - 3'," +
                "'Diğer adıyla DaBT-İPA- Hib (Difteri, Aselüler Boğmaca, Tetanos, İnaktif Polio, Hemofilus Influenza tip B aşısı.\n" +
                "Bu 5 hastalığa karşı geliştirilen karma aşı, 2 ay arayla (2, 4 ve 6. aylarda) 3 kez temel aşılama dizisi, 18-24. ayda 4. aşı (1. pekiştirme) olarak toplam 4 kez uygulanır'," +
                "180" +
                ");";

        String Besli_Karma_Pekistirme = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Beşli Karma - 4'," +
                "'Diğer adıyla DaBT-İPA- Hib (Difteri, Aselüler Boğmaca, Tetanos, İnaktif Polio, Hemofilus Influenza tip B aşısı.\n" +
                "Bu 5 hastalığa karşı geliştirilen karma aşı, 2 ay arayla (2, 4 ve 6. aylarda) 3 kez temel aşılama dizisi, 18-24. ayda 4. aşı (1. pekiştirme) olarak toplam 4 kez uygulanır'," +
                "540" +
                ");";

        String Pnomokok_1 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Pnömokok - 1'," +
                "'Diğer adıyla Konjüge Pnömokok Aşısı.\n" +
                "KPA 2, 4 ve 6. aylarda temel aşılama dizisi olarak 3 kez, 12. ayda (12-18 ay arasında yapılabilir) pekiştirme aşısı olarak 4. kez uygulanır'," +
                "60" +
                ");";

        String Pnomokok_2 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Pnömokok - 2'," +
                "'Diğer adıyla Konjüge Pnömokok Aşısı.\n" +
                "KPA 2, 4 ve 6. aylarda temel aşılama dizisi olarak 3 kez, 12. ayda (12-18 ay arasında yapılabilir) pekiştirme aşısı olarak 4. kez uygulanır'," +
                "120" +
                ");";

        String Pnomokok_3 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Pnömokok - 3'," +
                "'Diğer adıyla Konjüge Pnömokok Aşısı.\n" +
                "KPA 2, 4 ve 6. aylarda temel aşılama dizisi olarak 3 kez, 12. ayda (12-18 ay arasında yapılabilir) pekiştirme aşısı olarak 4. kez uygulanır'," +
                "180" +
                ");";

        String Pnomokok_Pekistirme = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Pnömokok - 4'," +
                "'Diğer adıyla Konjüge Pnömokok Aşısı.\n" +
                "KPA 2, 4 ve 6. aylarda temel aşılama dizisi olarak 3 kez, 12. ayda (12-18 ay arasında yapılabilir) pekiştirme aşısı olarak 4. kez uygulanır'," +
                "360" +
                ");";

        String KKK = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'KKK - 1'," +
                "'Diğer adıyla Kızamık, Kızamıkçık, Kabakulak Aşısı.\n" +
                "KKK aşısı 12. ayda ilk aşı ve 4-6 yaş arasında ya da İÖO 1. sınıfta pekiştirme aşısı olarak toplam 2 kez yapılır. Aşının uygulanmasında gecikme olan çocuklardan, en az bir ay arayla toplam 2 kez KKK aşısı yapılmalıdır. Bu bağlamda, her çocuğa 2 kez KKK aşısı yapılmış olmalıdır'," +
                "360" +
                ");";

        String KKK_Pekistirme = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'KKK - 2'," +
                "'Diğer adıyla Kızamık, Kızamıkçık, Kabakulak Aşısı.\n" +
                "KKK aşısı 12. ayda ilk aşı ve 4-6 yaş arasında ya da İÖO 1. sınıfta pekiştirme aşısı olarak toplam 2 kez yapılır. Aşının uygulanmasında gecikme olan çocuklardan, en az bir ay arayla toplam 2 kez KKK aşısı yapılmalıdır. Bu bağlamda, her çocuğa 2 kez KKK aşısı yapılmış olmalıdır'," +
                "2520" +
                ");";

        String DaBT_IPA = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Dörtlü Karma Aşı'," +
                "'Diğer adıyla DaBT - IPA.\n" +
                "Pekiştirme aşısıdır. Okul çağının ilk yıllarında, genellikle 1. sınıfta yapılır.'," +
                "2520" +
                ");";

        String OPA_1 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Çocuk Felci Aşısı - 1'," +
                "'Diğer adıyla DaBT - IPA.\n" +
                "Hastalık yapma gücü azaltılmış canlı virüs aşısıdır. Ağızdan uygulanır. Bütün çocuklara ulusal bağışıklama çizelge uyarınca 2 kez OPA yapılması önerilir.'," +
                "180" +
                ");";

        String OPA_2 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Çocuk Felci Aşısı - 2'," +
                "'Diğer adıyla Oral Polio Aşısı.\n" +
                "Hastalık yapma gücü azaltılmış canlı virüs aşısıdır. Ağızdan uygulanır. Bütün çocuklara ulusal bağışıklama çizelge uyarınca 2 kez OPA yapılması önerilir.'," +
                "540" +
                ");";

        String TD = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'TD'," +
                "'Diğer adıyla Difteri - Tetanos Aşısı (Erişkin tipi difteri) \n" +
                "Tetanos aşısı yerine TD aşısı uygulandığında çocukluk dönemindeki difteri aşılamasının pekiştirme dozu yapılmakta, önceden bağışıklanmamış difteriye duyarlı kişilerin de bağışıklanması sağlanmaktadır. Gebelik de dahil tetanos aşısı gereken her durumda TD aşısı uygulanmalıdır'," +
                "3960" +
                ");";

        String Hepatit_A_1 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Hepatit A - 1'," +
                "'Hepatit A aşısının 18 ve 24. aylarda 6 ay arayla 2 kez yapılması önerilir. 18 yaşın altında çocuk dozu olarak uygulanır. Erişkin dozu çocuk dozundan farklıdır ve genellikle çocuk dozunun 2 katıdır. Altı yaşından küçük çocuklarda aşılama doğrudan uygulanır; daha önce aşılanmamıış olan 6 yaşından büyüklerdeyse farklı yaklaşım olarak AntiHAV-IgG bakılabilir, negatifse aşılama yapılabilir.'," +
                "540" +
                ");";

        String Hepatit_A_2 = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Hepatit A - 2'," +
                "'Hepatit A aşısının 18 ve 24. aylarda 6 ay arayla 2 kez yapılması önerilir. 18 yaşın altında çocuk dozu olarak uygulanır. Erişkin dozu çocuk dozundan farklıdır ve genellikle çocuk dozunun 2 katıdır. Altı yaşından küçük çocuklarda aşılama doğrudan uygulanır; daha önce aşılanmamıış olan 6 yaşından büyüklerdeyse farklı yaklaşım olarak AntiHAV-IgG bakılabilir, negatifse aşılama yapılabilir.'," +
                "720" +
                ");";

        String Su_Cicegi = "INSERT INTO Bilgiler (" +
                "Asi_Ismi," +
                "Aciklama," +
                "Asi_Gunu )" +
                "VALUES (" +
                "'Suçiçeği'," +
                "'Suçiceği aşısının 12. ayda ilk doz ve 4-6 yaşında ya da İÖO 1. sınıfta pekiştirme aşısı olarak toplam 2 kez yapılması önerilir. Ancak ulusal bağışıklama çizelgemizde şimdilik yalnızca 1. yaşın bitiminde suçiçeği aşısı yapılmaktadır, pekiştirme aşısı uygulamada yoktur.'," +
                "360" +
                ");";

        db.execSQL(userTableSQL);
        db.execSQL(childTableSQL);
        db.execSQL(bilgilerTableSQL);
        db.execSQL(asiTableSQL);


        db.execSQL(Hepatit_B_1);
        db.execSQL(Hepatit_B_2);
        db.execSQL(Hepatit_B_3);

        db.execSQL(BCG);

        db.execSQL(Besli_Karma_1);
        db.execSQL(Besli_Karma_2);
        db.execSQL(Besli_Karma_3);
        db.execSQL(Besli_Karma_Pekistirme);

        db.execSQL(Pnomokok_1);
        db.execSQL(Pnomokok_2);
        db.execSQL(Pnomokok_3);
        db.execSQL(Pnomokok_Pekistirme);

        db.execSQL(KKK);
        db.execSQL(KKK_Pekistirme);

        db.execSQL(DaBT_IPA);

        db.execSQL(OPA_1);
        db.execSQL(OPA_2);

        db.execSQL(TD);

        db.execSQL(Hepatit_A_1);
        db.execSQL(Hepatit_A_2);

        db.execSQL(Su_Cicegi);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Child");
        db.execSQL("DROP TABLE IF EXISTS Bilgiler");
        db.execSQL("DROP TABLE IF EXISTS Asi_Tablosu");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
    }
}
