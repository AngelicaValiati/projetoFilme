package filme.projeto1.br.com.filme.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import filme.projeto1.br.com.filme.R;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeAdapterViewHolder> {

    private String[] mFilme;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final FilmeAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface FilmeAdapterOnClickHandler {
        void onClick(String filmeSelect);
    }

    public FilmeAdapter(FilmeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class FilmeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNameTittle;
        public final ImageView mImageFilme;

        public FilmeAdapterViewHolder(View view) {
            super(view);
            mNameTittle = (TextView) view.findViewById(R.id.tv_filme_name);
            mImageFilme = (ImageView) view.findViewById(R.id.iv_cartaz);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String film = mFilme[adapterPosition];
            mClickHandler.onClick(film);
        }
    }

    @Override
    public FilmeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.filme_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FilmeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmeAdapterViewHolder filmeAdapterViewHolder, int position) {
        String mFilmeSelected = mFilme[position];
        filmeAdapterViewHolder.mNameTittle.setText(mFilmeSelected);
        //filmeAdapterViewHolder.mImageFilme.setImageIcon(film);
    }

    @Override
    public int getItemCount() {
        if (null == mFilme) return 0;
        return mFilme.length;
    }

    public void setFilme(String[] filme) {
        mFilme = filme;
        notifyDataSetChanged();
    }
}
