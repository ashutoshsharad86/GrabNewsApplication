package com.ashutosh.grab.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashutosh.grab.R;
import com.ashutosh.grab.data.model.Article;
import com.ashutosh.grab.data.model.NewsResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private ArticleSelectedListener articleSelectedListener;
    private NewsResult data = new NewsResult("", 0, null);

    ArticleListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner,
            ArticleSelectedListener articleSelectedListener) {
        this.articleSelectedListener = articleSelectedListener;
        viewModel.getNewsResult().observe(lifecycleOwner, repos -> {
            if (repos != null) {
                data = repos;
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view, articleSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(data.articles.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if (data != null && data.articles != null) {
            return data.articles.size();
        }
        return 0;
    }

    @Override
    public void onViewAttachedToWindow(ArticleViewHolder holder) {
        holder.bind(data.articles.get(holder.getAdapterPosition()));
    }

    static final class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.articleimage)
        ImageView articleImage;
        @BindView(R.id.title)
        TextView articleTitle;
        @BindView(R.id.source)
        TextView articleSource;

        private Article article;

        ArticleViewHolder(View itemView, final ArticleSelectedListener articleSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (article != null) {
                    articleSelectedListener.onArticleSelected(article);
                }
            });
        }

        void bind(Article article) {
            this.article = article;
            articleTitle.setText(article.title);
            articleSource.setText(article.source.name);
            Glide.with(articleImage.getContext().getApplicationContext())
                    .load(article.urlToImage)
                    .placeholder(R.drawable.newsimage)
                    .error(R.drawable.newsimage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(articleImage);
        }
    }
}
