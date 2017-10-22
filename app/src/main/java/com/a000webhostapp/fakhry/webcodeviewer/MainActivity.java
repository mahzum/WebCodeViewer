package com.a000webhostapp.fakhry.webcodeviewer;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView tv;
    Spinner spin;
    Button btn;
    EditText in;
    public String url = null;
    ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv      = (TextView)    findViewById(R.id.textView);
        spin    = (Spinner)     findViewById(R.id.protocol);
        btn     = (Button)      findViewById(R.id.btnGet);
        in      = (EditText)    findViewById(R.id.UrlIn);
        load    = (ProgressBar) findViewById(R.id.pbLoad);

        load.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Spinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * url = zspinner.getSelectedItem() + zetInput.getText().toString();
                boolean valid = Patterns.WEB_URL.matcher(url).matches();

                if (valid){
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                    zpbLoad.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                } else {
                    Loader loader = getSupportLoaderManager().getLoader(0);
                    if (loader != null) {
                        loader.cancelLoad();
                    }
                    zpbLoad.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                    tv.setText("This Website is not Valid");
                }*/
                url = spin.getSelectedItem() + in.getText().toString();
                boolean valid = Patterns.WEB_URL.matcher(url).matches();

                if(valid){
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                    load.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                }else {
                    Loader loader = getSupportLoaderManager().getLoader(0);
                    if (loader != null){
                        loader.cancelLoad();
                    }
                    load.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                    tv.setText("this website is not valid");
                }
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderWeb(this, url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        load.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        tv.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
