package weather.miss.com.weather.model;

/**
 * Created by Administrator on 2015/12/14.
 */
public class County {
	/**
	 * id
	 */
	private int id;

	/**
	 * 国家名
	 */
	private String countyName;

	/**
	 * 国家编号
	 */
	private String countyCode;

	/**
	 * 城市Id
	 */
	private int cityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}

