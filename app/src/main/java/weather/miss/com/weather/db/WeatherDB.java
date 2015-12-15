package weather.miss.com.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import weather.miss.com.weather.model.City;
import weather.miss.com.weather.model.County;
import weather.miss.com.weather.model.Province;

/**
 * Created by Administrator on 2015/12/14.
 */
public class WeatherDB {
	public static final String DB_NAMW = "miss_weather";

	public static final int VERSION = 1;

	private  static WeatherDB weatherDB;
	private SQLiteDatabase db;

	/**
	 * 构造 方法初始化
	 * @param context
	 */
	private WeatherDB(Context context) {
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAMW, null, VERSION);
		this.db = dbHelper.getWritableDatabase();
	}


	/**
	 * 获取WeatherDB实例
	 */
	public synchronized static WeatherDB getInstanceDB(Context context) {
		if (null == weatherDB) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}

	/**
	 * 存入实体
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (null != province) {
			ContentValues values = new ContentValues();
			values.put("province_name",province.getProvinceName());
			values.put("province_code",province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * 查询所有的省份
	 * @return
	 */
	public List<Province> loadProvine() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province",null,null,null,null,null,null,null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				list.add(province);
			} while (cursor.moveToLast());
		}
        if (null != cursor)
			cursor.close();
		return list;
	}

	/**
	 *存储city
	 * @param city
	 */
	public void saveCity(City city) {
		if (null != city) {
			ContentValues values = new ContentValues();
			values.put("city_name",city.getCityName());
			values.put("city_code",city.getCityCode());
			values.put("province_id",city.getProvinceCode());
			db.insert("City",null,values);
		}
	}

	/**
	 * 根据省份查询城市
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCity(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City",null,"province_id=?",new String[] {String.valueOf(provinceId)},null,null,null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setProvinceCode(provinceId);
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			} while (cursor.moveToLast());
		}
		if (null != cursor)
			cursor.close();
		return list;
	}

	/*存储国家*/
	public void saveCounty(County county) {
		if (null != county) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	/**
	 * 根据城市查询国家
	 * @param cityId
	 * @return
	 */
	public List<County> loadCounty(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County",null,"city_id = ?",new String[] {String.valueOf(cityId)},null,null,null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCityId(cityId);
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				list.add(county);
			} while (cursor.moveToLast());
		}
		if (null != cursor)
			cursor.close();
		return list;
	}



}
