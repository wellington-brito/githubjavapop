package com.example.wellington_brito.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.example.wellington_brito.R;
import com.example.wellington_brito.adapters.RecyclerAdapter;
import com.example.wellington_brito.asyncTasks.RepositoriesAsyncTask;
import com.example.wellington_brito.model.Repository;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class MostStarredHome extends AppCompatActivity implements RecyclerAdapter.RepositoryListener, Filterable {

    public static final int CODIGO_ACTITIVITY = 1;
    final static String lista = "lista";
    ActionBarDrawerToggle toggle;
    List<Repository> repositories = new ArrayList<>();
    private List<Repository> itemsFiltered = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private SearchView searchView;
    RepositoriesAsyncTask repositoriesAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerViewRepositories);
        this.loadDataApi();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = ( androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public void loadDataApi() {
        this.repositoriesAsyncTask = new RepositoriesAsyncTask(this, recyclerView, recyclerAdapter);
        this.recyclerAdapter = new RecyclerAdapter(this, repositoriesAsyncTask.getList());
        this.repositoriesAsyncTask.execute();
    }

    @Override
    public void onSelected(Repository item) {
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public Filter getFilter() {
        repositories= repositoriesAsyncTask.getList();
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<Repository> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered = repositories;
                } else {
                    for (Repository repository : repositories) {
                        if (repository.getRepositoryName().toLowerCase().contains(query.toLowerCase()) || repository.getUsername().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(repository);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                //Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
                List<Repository> lista = (List<Repository>) results.values;
                for (Repository teste: lista ) {
                    Log.d("RESULTS===", teste.getRepositoryName());
                }
                atualizarLista(lista);
            }
        };
    }

    public void atualizarLista(List<Repository> lista) {
        if (lista.size() > 0){
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerAdapter = new RecyclerAdapter(this, lista);
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclerAdapter);
        }

    }
}