package pwr.itapps.meetme.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import pwr.itapps.meetme.exception.MainThreadOperationException;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

public class AbstractCommunicationHelper {

	private static final String TAG = "AbstractCommunicationHelper";
	private String serviceAddress;
	protected Context context;

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	private Integer timeOut;

	public AbstractCommunicationHelper(Context context, String serviceAddress) {
		this.serviceAddress = serviceAddress;
		this.timeOut = 10 * 1000;
		this.context = context;
	}

	public AbstractCommunicationHelper(Context context, String serviceAddress,
			Integer timeout) {
		this.serviceAddress = serviceAddress;
		this.timeOut = timeout;
		this.context = context;
	}

	protected String sendRequest(String body, String service)
			throws TimeoutException, IOException, MainThreadOperationException {
		Log.i(TAG, body);
		if (Looper.myLooper() == Looper.getMainLooper()) {
			throw new MainThreadOperationException();
		}

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), timeOut);
		HttpConnectionParams.setSoTimeout(client.getParams(), timeOut);

		HttpResponse response;
		try {
			HttpPost post = new HttpPost(serviceAddress + service);
			StringEntity se = new StringEntity(body);

//			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(se);
			response = client.execute(post);
			if (response != null) {
				String resp = readResponse(response, service);// , tag);
				return resp;
			}
			return null;
		} catch (ConnectTimeoutException te) {
			throw te;
		} catch (IOException ie) {
			throw ie;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	public void saveResponseToFile(String filename, String content) {
		String fName = filename;
		File file = new File(Environment.getExternalStorageDirectory(), fName);
		FileOutputStream fos;
		byte[] data = new String(content).getBytes();
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// handle exception
		} catch (IOException e) {
			// handle exception
		}
	}

	public String sendAsyncRequest(String json, String service) {
		final String jsonObject = json;
		final String serv = service;
		final String tag = service + "_" + System.currentTimeMillis();
		Thread t = new Thread() {
			public void run() {
				try {
					sendRequest(jsonObject, serv);
				} catch (Exception e) {
					e.printStackTrace();
				}// , tag);
			}
		};
		t.start();
		return tag;
	}

	protected static String readResponse(HttpResponse response, String service) {// String
																					// tag)
																					// {

		if (response == null) {
			return null;
		}

		HttpEntity entity2 = response.getEntity();
		String result = null;

		try {

			if (entity2 != null) {
				InputStream instream = entity2.getContent();
				result = convertStreamToString(instream);
				instream.close();

				Log.d(TAG, "SERVER RESPONSE: " + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	protected static String convertStreamToString(InputStream instream) {

		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(instream,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return writer.toString();
	}

	protected static String createInputStatement(String json) {
		return "{\"input\":" + json + "}";
	}

}
