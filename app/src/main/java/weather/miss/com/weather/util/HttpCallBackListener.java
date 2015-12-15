package weather.miss.com.weather.util;

/**
 * Created by Administrator on 2015/12/14.
 */
public interface HttpCallBackListener {
	void onFinish(String response);

	void Error(Exception e);
}
