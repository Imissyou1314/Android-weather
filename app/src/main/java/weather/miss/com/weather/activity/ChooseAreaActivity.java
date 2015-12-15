package weather.miss.com.weather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import weather.miss.com.weather.R;
import weather.miss.com.weather.db.WeatherDB;
import weather.miss.com.weather.model.City;
import weather.miss.com.weather.model.County;
import weather.miss.com.weather.model.Province;
import weather.miss.com.weather.util.HttpCallBackListener;
import weather.miss.com.weather.util.HttpUtil;
import weather.miss.com.weather.util.Utility;

/**
 * Created by Administrator on 2015/12/14.
 */
public class ChooseAreaActivity extends Activity {

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private ProgressDialog progressDialog;
	private TextView titleView;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private WeatherDB weatherDB;
	private List<String> dataList = new ArrayList<String>();

	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;

	private Province selectedProvince;
	private City selectedCity;
	private County selectedCounty;

	private int currentLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		initViews();
		listView.setAdapter(adapter);
		initData();
	}

	private void initData() {
		weatherDB = weatherDB.getInstanceDB(this);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(position);
					queryCity();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounty();
				}
			}
		});
		queryProvices();
	}


	private void initViews() {
		listView = (ListView) findViewById(R.id.list_view);
		titleView = (TextView) findViewById(R.id.titel_text);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);

	}

	/**
	 *查询城市
	 */
	private void queryCity() {
		cityList = weatherDB.loadCity(selectedProvince.getId());
		if (null != cityList && cityList.size() > 0 ) {
			dataList.clear();
			for (City city: cityList)
				dataList.add(city.getCityName());
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvinceCode(),"city");
		}
	}

	/**
	 * 查询国家
	 */
	private void queryCounty() {
		countyList = weatherDB.loadCounty(selectedCity.getId());
		if (null != countyList && countyList.size() > 0 ) {
			dataList.clear();
			for (County county : countyList)
				dataList.add(county.getCountyName());
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			queryFromServer(selectedCity.getCityCode(),"county");
		}
	}

	/**
	 * 查询省份
	 */
	private void queryProvices(){
		provinceList = weatherDB.loadProvine();
		if (null != provinceList && provinceList.size() >0 ) {
			dataList.clear();
			for (Province province : provinceList)
				dataList.add(province.getProvinceName());
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}
	}

	/**
	 * 从服务器查询
	 * @param code
	 * @param type
	 */
	private void queryFromServer(final String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn.data/list3/city"+ code +".xml";
		} else {
			address ="http://www/weather.com.cn.data.list3.city.xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequset(address, new HttpCallBackListener() {
			@Override
			public void onFinish(String response) {
				boolean result = false;
				if ("province".equals(type))
					result =Utility.handleProvincesResponse(weatherDB,response);
				else if ("city".equals(type))
					result = Utility.handleCityResponse(weatherDB,response,selectedProvince.getId());
				else if ("county".equals(type));
					result = Utility.handleCountyResponse(weatherDB,response,selectedCity.getId());
				if (result) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							closeProgressDialog();
							if ("province".equals(type))
								queryProvices();
							else if ("city".equals(type))
								queryCity();
							else if ("county".equals(type))
								queryCounty();
						}
					});
				}
			}

			@Override
			public void Error(Exception e) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this,"加载失败.....",Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}

	/**
	 * 显示对话框
	 */
	private void showProgressDialog() {
		if (null == progressDialog) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载......");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	public void onBackPressed() {
		if (currentLevel == LEVEL_COUNTY)
			queryCity();
		else if (currentLevel == LEVEL_CITY)
			queryProvices();
		else
			finish();
	}

}
