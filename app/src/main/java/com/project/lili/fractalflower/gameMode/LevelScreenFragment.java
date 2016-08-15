package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.project.lili.fractalflower.AnimView;
import com.project.lili.fractalflower.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "height";
    private static final String ARG_PARAM2 = "width";

    // TODO: Rename and change types of parameters
    private int height;
    private int width;

    private OnFragmentInteractionListener mListener;

    public LevelScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LevelScreenFragment newInstance(int param1, int param2) {
        LevelScreenFragment fragment = new LevelScreenFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            height = getArguments().getInt(ARG_PARAM1);
            width = getArguments().getInt(ARG_PARAM2);
        }

        System.out.println("level screen fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level_screen, container, false);

        FrameLayout layout = (FrameLayout) view.findViewById(R.id.level_screen_layout);
        ViewGroup.LayoutParams params = layout.getLayoutParams();

        System.out.println("height " + height + ", width " + width);

        params.height = height/2;
        params.width = width;
        layout.setLayoutParams(params);

        AnimView animView = new AnimView(this.getContext());
        animView.setBackgroundColor(Color.GRAY);
        layout.addView(animView);

        return view;

        //return inflater.inflate(R.layout.fragment_level_screen, container, false);
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
    }
}
