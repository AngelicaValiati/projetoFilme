package filme.projeto1.br.com.filme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import filme.projeto1.br.com.filme.Adapter.FilmeAdapter;
import filme.projeto1.br.com.filme.Data.FilmesPreference;
import filme.projeto1.br.com.filme.Utils.NetworkUtils;
import filme.projeto1.br.com.filme.Utils.OpenFilmeJsonUtils;

public class MainActivity extends AppCompatActivity implements FilmeAdapter.FilmeAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private FilmeAdapter mFilmeAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_filme);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_erro_filme);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mFilmeAdapter = new FilmeAdapter(this);
        mRecyclerView.setAdapter(mFilmeAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadFilme();
    }

    private void loadFilme() {
        showFilmeView();

        new FetchFilmTask().execute();
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param film The film for the id that was clicked
     */
    @Override
    public void onClick(String film) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, film);
        startActivity(intentToStartDetailActivity);
    }

    private void showFilmeView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchFilmTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL filmRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonFilmResponse = NetworkUtils
                        .getResponseFromHttpUrl(filmRequestUrl);

                String[] simpleJsonFilmData = OpenFilmeJsonUtils.
                        getSimpleFilmStringsFromJson(MainActivity.this, jsonFilmResponse);

                return simpleJsonFilmData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] filme) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (filme != null) {
                showFilmeView();
                mFilmeAdapter.setFilme(filme);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mFilmeAdapter.setFilme(null);
            loadFilme();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
