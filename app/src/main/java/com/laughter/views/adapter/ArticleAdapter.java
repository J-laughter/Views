package com.laughter.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.laughter.views.R;
import com.laughter.views.model.Article;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends BaseAdapter {

    private List<Article> articles;
    private Context mContext;

    public ArticleAdapter(Context context, List<Article> articles){
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = articles.get(position);
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDate.setText(article.getDate());
        holder.tvTitle.setText(article.getTitle());
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvTag.setText(article.getChapterName());

        return convertView;
    }

    class ViewHolder {
        View itemView;
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_author) TextView tvAuthor;
        @BindView(R.id.tv_tag) TextView tvTag;
        @BindView(R.id.ib_cancle) ImageButton ibCancle;
        ViewHolder(View view){
            this.itemView = view;
            ButterKnife.bind(this, itemView);
        }
    }
}
