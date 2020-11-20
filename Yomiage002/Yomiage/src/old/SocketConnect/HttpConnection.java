package old.SocketConnect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

	// init httpConnection
	private HttpURLConnection http;
	private InputStream is;
	private OutputStream os;

	/**
	 * HTTP通信用のクラス
	 * 
	 * @param url    URL指定用の変数
	 * @param option 読み込み、書き込み指定
	 * @throws IOException
	 */
	public HttpConnection(String url, String option) throws IOException {
		URL select = new URL(url);

		// connection
		this.http = (HttpURLConnection) select.openConnection();

		// Get Method Connection setup
		this.http.setRequestMethod("GET");
		// Write setup
		if (option.indexOf("w") > -1) {
			this.http.setDoOutput(true);
		}
		// HTTPcode 200 の処理
		if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

			this.is = this.http.getInputStream();

		} else {
			// HTTP code 200以外の処理
			throw new IOException(String.format("HttpCode:%d\nmsg:%s", this.http.getResponseCode(),
					this.http.getResponseMessage()));
		}

	}

	// getter and setter
	public HttpURLConnection getHttp() {
		return this.http;
	}

	public void setHttp(HttpURLConnection http) {
		this.http = http;
	}

	public InputStream getIs() {
		return this.is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}
}
