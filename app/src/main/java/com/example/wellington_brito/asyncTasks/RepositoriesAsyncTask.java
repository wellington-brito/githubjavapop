package com.example.wellington_brito.asyncTasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellington_brito.R;
import com.example.wellington_brito.adapters.RecyclerAdapter;
import com.example.wellington_brito.model.Repository;
import com.example.wellington_brito.view.MostStarredHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepositoriesAsyncTask extends AsyncTask<String, String, String>{

    ProgressDialog progessDialog;
    Context context;
    List<Repository> repositories = new ArrayList<>();
    ListView mListRepositories;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public RepositoriesAsyncTask(Context context, ListView mList) {
        this.context = context;
        this.mListRepositories = mList;
    }

    public RepositoriesAsyncTask(MostStarredHome mostStarredHome, RecyclerView recyclerView, RecyclerAdapter recyclerAdapter) {
        this.context = mostStarredHome;
        this.recyclerView = recyclerView;
        this.recyclerAdapter = recyclerAdapter;
    }


    //Responsavel por carregar o Objeto JSON
    public static String getJSONFromAPI(String url) {
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    @NonNull
    private static String converterInputStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null) {
                buffer.append(linha);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    @Override
    protected void onPreExecute() {
        this.progessDialog = ProgressDialog.show(context, context.getString(R.string.TituloEspera), context.getString(R.string.MensagemEspera), false, false);
    }


    //Chama o método para conectar com a API
    @Override
    protected String doInBackground(String... strings) {
        try {
            return this.getJSONFromAPI("https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject mJsonObjectFullData = new JSONObject(result);
            JSONArray json = mJsonObjectFullData.getJSONArray("items");
            for (int i = 0; i < json.length(); i++) {
                mJsonObjectFullData = (JSONObject) json.get(i);
                Repository repository = new Repository();
                repository.setUsername(mJsonObjectFullData.getJSONObject("owner").getString("login"));
                repository.setRepositoryName(mJsonObjectFullData.getString("name"));
                repository.setLastName(mJsonObjectFullData.getString("full_name"));
                repository.setRepositoryDescricpiton(mJsonObjectFullData.getString("description"));
                repository.setCountStars(mJsonObjectFullData.getString("stargazers_count"));
                repository.setCountForks(mJsonObjectFullData.getString("forks"));
                this.repositories.add(repository);
            }
            this.exibirDados();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.progessDialog.dismiss();
    }

    @SuppressLint("ResourceType")
    public void exibirDados() throws Exception {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerAdapter = new RecyclerAdapter(context, repositories);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public List<Repository> getList() {
        return repositories;
    }
}
