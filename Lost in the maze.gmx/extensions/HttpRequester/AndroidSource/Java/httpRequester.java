package ${YYAndroidPackageName};

import android.util.Log;
import java.io.BufferedReader;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.*;
import org.json.JSONObject;

import ${YYAndroidPackageName}.R;
import com.yoyogames.runner.RunnerJNILib;

public class httpRequester {
	private static final int EVENT_OTHER_SOCIAL = 70;
	private static final int EVENT_OTHER_HTTP = 62;
	int _id = 1000000;

	public double httpRequest(String _url, String _method, String _header, String _body) {
		final int id = (_id++);
		final String __url = _url;
		final String __method = _method;
		final String __header = _header;
		final String __body = _body;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int rCode = 0;
				try {
					boolean hasBody = false;
					boolean hasError = false;
					boolean hasException = false;
					int status = 0;
					//Log.i("yoyo", "httpRequest thread id = " + id);
					//Log.i("yoyo", "--------- ");
					//Log.i("yoyo", "     url: " + __url);
					String method = __method.toUpperCase();
					//Log.i("yoyo", "  method: " + method);
					//Log.i("yoyo", " headers: " + __header);
					//Log.i("yoyo", "    body: " + __body);
					//Log.i("yoyo", "--------- ");
					URL url = new URL(__url);
					HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
					if (method.equals("POST") || method.equals("PUT")) hasBody = true;
					httpCon.setDoOutput(hasBody);
					httpCon.setRequestMethod(method);
					//Request headers
					String headerKey;
					String headerVal;
					JSONObject jObject = new JSONObject(__header);
					Iterator<?> headerKeys = jObject.keys();
					while (headerKeys.hasNext()) {
						headerKey = (String)headerKeys.next();
						headerVal = jObject.getString(headerKey);
						//Log.i("yoyo", "Set header " + headerKey + ": " + headerVal);
						httpCon.setRequestProperty(headerKey, headerVal);
					}
					if (hasBody == false) {
						//Don't send a request body and connect directly
						//Log.i("yoyo", "Connecting...");
						httpCon.connect();
					} else {
						//Send request body
						//Log.i("yoyo", "Sending request...");
						OutputStreamWriter out = new OutputStreamWriter(
							httpCon.getOutputStream()
							);
						out.write(__body);
						out.close();
					}
					rCode = httpCon.getResponseCode();
					//Log.i("yoyo", "HTTP response code " + rCode);
					if (rCode >= 400) {
						//Log.i("yoyo", "HTTP error!");
						hasError = true;
					}
					InputStreamReader in;
					if (hasError == false) {
						in = new InputStreamReader(httpCon.getInputStream());
					} else {
						//Log.i("yoyo", "*redirected Input stream to Error stream");
						in = new InputStreamReader(httpCon.getErrorStream());
					}
					BufferedReader br = new BufferedReader(in);
					//Print multi-line request response
					String httpResponse = "";
					String _httpResponse;
					int currLine = 0;
					//Log.i("yoyo", "Server replied with: ");
					_httpResponse = br.readLine();
					while (_httpResponse != null) {
						//Log.i("yoyo", String.format("%1$4s", Integer.toString(currLine)) + ": " + _httpResponse);
						httpResponse += _httpResponse;
						_httpResponse = br.readLine();
						currLine++;
					}
					//Log.i("yoyo", "Total reply size: " + currLine + ((currLine == 1) ? " line" : " lines"));
					//Reply headers
					//Headers are now sent via dsMaps. //*** indicates the deprecated JSON String passing functionality
					//***String rHeaders = "{";
					int dsMap_rHeaders = RunnerJNILib.jCreateDsMap(null, null, null);
					//Log.i("yoyo", "Created response headers dsMap with index " + dsMap_rHeaders);
					//Log.i("yoyo", "Response headers:");
					Map<String, List<String>> map = httpCon.getHeaderFields();
					for (Map.Entry<String, List<String>> entry : map.entrySet()) {
						//***if (rHeaders.length() > 1) {
						//***	rHeaders += ", ";
						//***}
						//***rHeaders += "\"" + entry.getKey() + "\": ";
						//***rHeaders += "\"" + entry.getValue().get(0) + "\"";
						//The key might be 0 - Maps use String as keys. This will crash Game Maker
						if (entry.getKey() == null) {
							RunnerJNILib.DsMapAddString(dsMap_rHeaders, "null", entry.getValue().get(0));
						} else {
							RunnerJNILib.DsMapAddString(dsMap_rHeaders, entry.getKey(), entry.getValue().get(0));
						}
						//Log.i("yoyo", "Key : " + entry.getKey() + " ,Value : " + entry.getValue().get(0));
					}
					//***rHeaders += "}";
					//***//Log.i("yoyo", "JSON: " + rHeaders);
					httpCon.disconnect();
					//Send callback to GM:S
					int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "id", (double)id);
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "http_status", (double)rCode);
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "response_headers", (double)dsMap_rHeaders);
					RunnerJNILib.DsMapAddString(dsMapIndex, "result", httpResponse);
					RunnerJNILib.DsMapAddString(dsMapIndex, "class", "http");
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", (double)status);
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_HTTP);
				} catch (Exception err) {
					//Send callback to GM:S
					int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "id", (double)id);
					if (err instanceof MalformedURLException) {
						//Log.i("yoyo", "Exception in thread " + id + ":\nMalformed URL!");
						RunnerJNILib.DsMapAddString(dsMapIndex, "error", "malformed_url");
					} else {
						//Log.i("yoyo", "Unhandled exception in thread " + id + "!");
						RunnerJNILib.DsMapAddString(dsMapIndex, "error", "unhandled_exception");
					}
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "http_status", (double)rCode);
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "response_headers", -1);
					RunnerJNILib.DsMapAddString(dsMapIndex, "result", "");
					RunnerJNILib.DsMapAddString(dsMapIndex, "class", "http");
					RunnerJNILib.DsMapAddDouble(dsMapIndex, "status", -1);
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_HTTP);
				}
				//Log.i("yoyo", "Thread finished (id = " + id + ")");
				//Log.i("yoyo", " ");
			}
		}).start();
		return (double)id;
	}
}
