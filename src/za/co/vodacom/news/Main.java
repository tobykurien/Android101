package za.co.vodacom.news;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import za.co.vodacom.news.adapter.TweetAdapter;
import za.co.vodacom.news.data.Tweet;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {
	public static final String LOG_TAG = "Vodacom";

	Handler handler = new Handler();
	ProgressDialog pd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pd = new ProgressDialog(this);
		pd.setMessage("Loading..."); // extern this string
		pd.show();

		AsyncTask<Void, Void, Tweet[]> task = new AsyncTask<Void, Void, Tweet[]>() {
			@Override
			protected Tweet[] doInBackground(Void... params) {
				// getTweetsTest()
				String jsonData;
				Tweet[] data;
				try {
					jsonData = getData("https://api.twitter.com/1/statuses/user_timeline/vodacom.json");
					
					JSONArray entries = new JSONArray(jsonData);
					data = new Tweet[entries.length()];
					for (int i = 0; i < entries.length(); i++) {
						JSONObject post = entries.getJSONObject(i);
						data[i] = new Tweet();
						data[i].setTweet(post.getString("text"));
						data[i].setDate(post.getString("created_at"));
					}
				} catch (final Exception e) {
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(Main.this, "ERROR: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					});
					return null;
				}

				return data;
			}

			@Override
			protected void onPostExecute(Tweet[] tweets) {
				if (tweets != null) {
					TweetAdapter adapter = new TweetAdapter(Main.this, tweets);
					ListView lv = (ListView) findViewById(R.id.main_list_view);
					lv.setAdapter(adapter);
				}

				pd.dismiss();
			}
		};

		task.execute(new Void[0]);
	}

	public void onRefresh(View v) {

	}

	public void onExit(View v) {
		finish();
	}

	/**
	 * Get data from the internet
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String getData(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		c.connect();
		if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream is = c.getInputStream();
			byte[] buf = new byte[1024];
			int len;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			return os.toString();
		}

		return null;
	}

	public void onTest(View v) throws InterruptedException {
		if (Debug.BUTTON_TEST)
			Log.d(Main.LOG_TAG, "onTest() called!");

		// Toast.makeText(Main.this, "This is test!", Toast.LENGTH_LONG).show();
		Intent i = new Intent(Intent.ACTION_DIAL);
		i.setData(Uri.parse("tel:124345456"));
		startActivity(Intent.createChooser(i, "Select dialer"));

		if (Debug.BUTTON_TEST)
			Log.d(Main.LOG_TAG, "intent sent");
	}

	public Tweet[] getTweetTest() {
		Tweet[] tweets = new Tweet[20];
		for (int i = 0; i < 20; i++) {
			if (i == 10)
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(Main.this, "test", Toast.LENGTH_LONG)
								.show();
					}
				});

			Tweet t = new Tweet();
			t.setTweet("This is tweet number " + i);
			t.setDate("01/02/2012");
			tweets[i] = t;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tweets;
	}
}