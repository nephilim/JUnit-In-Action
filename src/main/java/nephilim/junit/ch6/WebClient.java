package nephilim.junit.ch6;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {
	public String getContent(URL url) {
		StringBuffer content = new StringBuffer();
		try {
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);

			InputStream is = connection.getInputStream();
			byte[] buffer = new byte[2048];
			int count;
			while ((count = is.read(buffer)) != -1) {
				content.append(new String(buffer, 0, count));
			}
		} catch (IOException e) {
			return null;
		}
		return content.toString();
	}
}
