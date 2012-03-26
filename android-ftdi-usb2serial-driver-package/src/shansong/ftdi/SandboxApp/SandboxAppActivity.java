package shansong.ftdi.SandboxApp;

import android.app.Activity;
import android.os.Bundle;
import shansong.ftdi.d2xx.*;

public class SandboxAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}