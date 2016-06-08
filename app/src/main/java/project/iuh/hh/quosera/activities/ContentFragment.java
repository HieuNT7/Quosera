package project.iuh.hh.quosera.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HieuNT on 4/10/2016.
 */
public class ContentFragment extends Fragment {
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstance) {
        //demo
        TextView textView = new TextView(container.getContext());
        textView.setText("Quosera");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
