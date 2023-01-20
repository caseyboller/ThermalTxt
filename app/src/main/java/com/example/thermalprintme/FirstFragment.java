package com.example.thermalprintme;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstFragment extends Fragment {
    private TextView textBox;
    private Button getButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Initialize views
        textBox = view.findViewById(R.id.messageBox);
        getButton = view.findViewById(R.id.button_getMsg);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckMessagesTask(getContext()).execute();
            }
        });

        return view;
    }

    private class CheckMessagesTask extends AsyncTask<Void, Void, String> {

        private OkHttpClient client;

        public CheckMessagesTask(Context context) {
            client = new OkHttpClient();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder().url("http://192.168.0.120:3000/messages").build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null) {
                return;
            }
            try {
                JSONArray messages = new JSONArray(response);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < messages.length(); i++) {
                    sb.append(messages.getJSONObject(i).getString("text")).append("\n");
                }
                textBox.setText(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
