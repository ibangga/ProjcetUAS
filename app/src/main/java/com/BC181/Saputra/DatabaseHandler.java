package com.BC181.Saputra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //menambah data Film 1
        try{
            tempDate = sdFormat.parse("2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "Star Wars Episode:VIII The Last Jedi",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Action, superhero",
                "Mark Hamill (luke skywalker), Carrier Fisher (Jendral Leia Organa), Adam Driver (Kylo Ren)",
                "Rey (Daisy Ridley) akhirnya berhasil menemukan ksatria legendaris Jedi, Luke Skywalker (Mark Hamill) di sebuah pulau dengan aura magis. Para pahlawan dari The Force Awakens diantaranya Leia, Finn dan Poe bergabung dengan sang legenda dalam sebuah petualangan epik, yang membuka misteri kuno The Force dimasa lalu yang mengejutkan."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke 2
        try{
            tempDate = sdFormat.parse("2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Thor:Ragnarok",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Action, Superhero",
                "Chris Hemsworth (Thor), Tom Hiddelstone (Loki)",
                "Dipenjara, Thor yang hebat menemukan dirinya dalam sebuah kontes gladiator yang mematikan melawan Hulk, mantan sekutunya. Thor harus berjuang untuk bertahan hidup dan berpacu melawan waktu untuk mencegah Hela yang sangat kuat menghancurkan rumah dan peradaban Asgardian." );
        tambahFilm(film2, db);
        idFilm++;

        // Tambah Film 3

        try{
            tempDate = sdFormat.parse("2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Spiderman Homecoming",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Action, Superhero",
                "Will Smith (Detektif Mike Lowrey), Téa Leoni (Julie Mott), Martin Lawrence (Detektif Marcus Burnett)",
                "Peter Parker berusaha menyeimbangkan dua. \n" +
                        "kehidupannya yang sangat bertolak belakang - menghentikan aksi penjualan senjata Chitauri oleh Adrian Toomes dan menjalani hari-hari selayaknya murid SMA.\n");
        tambahFilm(film3, db);


    }

}