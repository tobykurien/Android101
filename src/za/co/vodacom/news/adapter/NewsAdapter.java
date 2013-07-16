package za.co.vodacom.news.adapter;

import za.co.vodacom.news.R;
import za.co.vodacom.news.data.NewsItem;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	Context context;
	NewsItem[] newsItems;

	public NewsAdapter(Context ctx, NewsItem[] data) {
		this.context = ctx;
		this.newsItems = data;
	}

	@Override
	public int getCount() {
		return newsItems.length;
	}

	@Override
	public NewsItem getItem(int row) {
		return newsItems[row];
	}

	@Override
	public long getItemId(int row) {
		return row;
	}

	@Override
	public View getView(int row, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.news_row, parent, false);
		}

		NewsItem item = getItem(row);
		TextView title = (TextView) convertView.findViewById(R.id.news_title);
		title.setText(Html.fromHtml(item.getTitle()));

		TextView date = (TextView) convertView.findViewById(R.id.news_date);
		date.setText(item.getDate());

		TextView content = (TextView) convertView.findViewById(R.id.news_content);
		content.setText(Html.fromHtml(item.getContent()));
		
		return convertView;
	}

}
