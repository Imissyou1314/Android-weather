package weather.miss.com.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Imissyou on 2015/12/14.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 省份
     */
    public static final String CREARTE_PROVINCE = "create table Province(" +
            "id integer primary key autoincrement," +
            "province_name text," +
            "province_code text)";

    /**
     * 城市
     */
    public static final String CREARTE_CITY ="create table City(" +
            " id integer primary key autoincrement," +
            "city_name text," +
            "city_code text," +
            "province_id integer)";

    /**
     * 国家
     */
    public static final String CREARTE_COUNTY="create table County(" +
            "id integer primary key autoincrement," +
            "county_name text," +
            "county_code text," +
            "city_id integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREARTE_PROVINCE);
        db.execSQL(CREARTE_CITY);
        db.execSQL(CREARTE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
