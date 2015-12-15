package weather.miss.com.weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Administrator on 2015/12/14.
 */
public class HttpUtil {

	public static void sendHttpRequset(final String address, final HttpCallBackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = null;
					url = new URL(address);
					connection =(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuffer response = new StringBuffer();
					String line;
					while (null != (line = reader.readLine())) {
						response.append(line);
					}
					if (listener != null)
						listener.onFinish(response.toString());
				} catch (IOException e) {
					if (null != listener)
						listener.Error(e);
				} finally {
					if (null != connection)
						connection.disconnect();
				}
			}
		}).start();
	}
}
