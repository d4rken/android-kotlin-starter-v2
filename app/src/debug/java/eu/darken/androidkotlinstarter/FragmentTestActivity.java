//package eu.darken.androidkotlinstarter;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.widget.LinearLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import eu.darken.mvpbakery.injection.ManualInjector;
//import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector;
//
//public class FragmentTestActivity extends AppCompatActivity implements HasManualFragmentInjector {
//
//    ManualInjector<Fragment> manualInjector;
//
//    @SuppressLint("ResourceType")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LinearLayout view = new LinearLayout(this);
//        view.setId(1);
//        setContentView(view);
//    }
//
//    public void setManualInjector(ManualInjector<Fragment> manualInjector) {
//        this.manualInjector = manualInjector;
//    }
//
//    @Override
//    public ManualInjector<Fragment> supportFragmentInjector() {
//        return manualInjector;
//    }
//
//}
