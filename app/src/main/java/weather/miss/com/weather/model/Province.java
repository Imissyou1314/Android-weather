package weather.miss.com.weather.model;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Province {
	/**
	 * 省份
	 */
	private int id;
	/**
	 * 省份名
	 */
	private String provinceName;
	/**
	 * 省份编号
	 */
	private String provinceCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
}
