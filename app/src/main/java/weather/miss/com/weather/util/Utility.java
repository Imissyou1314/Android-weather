package weather.miss.com.weather.util;

import android.text.TextUtils;

import weather.miss.com.weather.db.WeatherDB;
import weather.miss.com.weather.model.City;
import weather.miss.com.weather.model.County;
import weather.miss.com.weather.model.Province;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Utility {
	public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (null != allProvinces  &&  allProvinces.length > 0) {
				for (String str : allProvinces) {
					String[] array = str.split("\\|");
					Province province = new Province();
					province.setProvinceName(array[1]);
					province.setProvinceCode(array[0]);
					weatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	public synchronized static boolean handleCityResponse(WeatherDB weatherDB, String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCity = response.split(",");
			if (null != allCity  &&  allCity.length > 0) {
				for (String str : allCity) {
					String[] array = str.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceCode(provinceId);
					weatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	public synchronized static boolean handleCountyResponse(WeatherDB weatherDB, String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounty = response.split(",");
			if (null != allCounty  &&  allCounty.length > 0) {
				for (String str : allCounty) {
					String[] array = str.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					weatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
