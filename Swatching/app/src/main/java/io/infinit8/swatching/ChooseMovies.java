package io.infinit8.swatching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_movies);

        getSupportActionBar().hide();

        ListView lv = (ListView)findViewById(R.id.listMovies);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseMovies.this, android.R.layout.simple_list_item_1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero est, nec pulvinar velit venenatis sit amet. Nulla neque metus, tristique in varius a, viverra nec sapien. Vestibulum at nibh risus. Fusce hendrerit congue aliquam. Phasellus sed dictum diam, sit amet iaculis nulla. Ut faucibus eleifend quam eget aliquet. Cras eu lorem vitae massa pulvinar rhoncus. Duis tincidunt tellus vitae dapibus sodales. Vivamus sit amet auctor tortor. Praesent tempor sed quam ac consequat. Donec tempor odio at erat eleifend viverra. Nulla efficitur magna eu risus hendrerit egestas. Nulla faucibus ante et sem imperdiet, quis dapibus ex tempor. Proin scelerisque scelerisque mattis. Nulla pulvinar orci purus, at condimentum nunc convallis a. Donec consectetur dolor sit amet tincidunt ornare. Nunc lobortis turpis sit amet mauris efficitur eleifend. Cras sed mauris convallis, aliquet neque sed, ultricies nulla. Morbi non vulputate turpis, a molestie tellus. Aliquam nibh nibh, convallis at turpis eget, lacinia pretium dui. Maecenas pellentesque est sed magna bibendum pellentesque eu a urna. Proin fermentum aliquam eros nec rutrum. Donec quis aliquet ex, quis ultricies eros. Phasellus at maximus elit. Nunc accumsan mauris sit amet mauris aliquet, vel ultrices neque ornare. Pellentesque vitae mi tristique, efficitur elit nec, blandit dolor. Aliquam.".split(" "));

        lv.setAdapter(adapter);

    }
}
