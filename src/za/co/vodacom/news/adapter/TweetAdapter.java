package za.co.vodacom.news.adapter;

import za.co.vodacom.news.R;
import za.co.vodacom.news.data.Tweet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetAdapter extends BaseAdapter {
	private Context context;
	private Tweet[] tweets;

	public TweetAdapter(Context context, Tweet[] tweets) {
		this.context = context;
		this.tweets = tweets;
	}
	
	public int getCount() {
		return tweets.length;
	}

	public Tweet getItem(int position) {
		return tweets[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.mail_list_item, null);
		}
		
		TextView tv1 = (TextView) convertView.findViewById(R.id.main_list_item_text);
		TextView tv2 = (TextView) convertView.findViewById(R.id.main_list_item_date);

		Tweet tweet = getItem(position);
		tv1.setText(tweet.getTweet());
		tv2.setText(tweet.getDate());
		
		return convertView;
	}

}
