package weather.miss.com.weather.model;

/**
 * Created by Administrator on 2015/12/14.
 */
public class City {
	/**
	 * 城市ID
	 */
	private int id;

	/**
	 * 	城市名
	 */
	private String cityName;

	/**
	 * 城市编号
	 */
	private String cityCode;

	/**
	 * 省份编号
	 */

	private int provinceCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}
}
