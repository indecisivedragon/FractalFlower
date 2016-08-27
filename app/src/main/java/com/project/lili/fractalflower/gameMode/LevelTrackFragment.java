package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.lili.fractalflower.AnimView;
import com.project.lili.fractalflower.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelTrackFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelTrackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelTrackFragment extends Fragment{
    private String TRACK_DEBUG = "level track fragment";

    //take in height and width of screen
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String ROTATION = "rotation";

    private int height;
    private int width;
    private int rotation;

    //this is the view to be called
    private View view;

    //text string for update
    String updateStatus = "...";

    private OnFragmentInteractionListener mListener;

    public LevelTrackFragment() {
        // Required empty public constructor
    }

    /**
     * sets height and width from parent view
     *
     * @param height screen height
     * @param width screen width
     * @return A new instance of fragment LevelTrackFragment.
     */
    public static LevelTrackFragment newInstance(int height, int width, int rotation) {
        LevelTrackFragment fragment = new LevelTrackFragment();
        Bundle args = new Bundle();
        args.putInt(HEIGHT, height);
        args.putInt(WIDTH, width);
        args.putInt(ROTATION, rotation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            height = getArguments().getInt(HEIGHT);
            width = getArguments().getInt(WIDTH);
            rotation = getArguments().getInt(ROTATION);
        }
        System.out.println("level track fragment created");

        mListener.onFragmentStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_level_track, container, false);

        setStatusText();

        FrameLayout layout = (FrameLayout) view.findViewById(R.id.fragment_level_track_id);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();

        System.out.println("level track fragment input: height " + height + ", width " + width + ", rotation " + rotation);

        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            params.height = height/3;
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        }
        else if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            params.height = height/2;
            params.width = width/3;
            params.gravity = Gravity.START;
        }
        layout.setLayoutParams(params);

        /*
        AnimView animView = new AnimView(this.getContext());
        animView.setBackgroundColor(Color.WHITE);
        layout.addView(animView);
        */

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
        Log.d(TRACK_DEBUG, "level track fragment received: " + this.updateStatus);
        setStatusText();
    }

    public void setStatusText() {
        TextView textView = (TextView) view.findViewById(R.id.level_track_notice);
        textView.setText(updateStatus);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onFragmentStart();

        void setStatusUpdate(String s);
    }
}
