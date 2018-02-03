package com.bild4.mror;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RSSFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RSSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RSSFragment extends Fragment {
    Handler handler;

    //    private static rssAdapter adapter;
    ArrayAdapter arrayAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public RSSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RSSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RSSFragment newInstance() {
        RSSFragment fragment = new RSSFragment();


        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        super.onCreate(savedInstanceState);
        Comm.getSocket().addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket websocket, final String message) throws Exception {

                if (message.startsWith("RSS_GET$")) {
                    handler.post(new Runnable() {
                        public void run() {
//                            Log.d("recieved", message);
                            String s = message.substring(message.indexOf("$") + 1,message.length()-1);
                            String[] list = s.split(";");
                            ArrayList<String> list2 = new ArrayList<String>();
                            for(int i=0;i<list.length;i++){
                                list2.add(list[i]);
//                                Log.d("line", list[i]);
                            }

// Create the adapter to convert the array to views
// Attach the adapter to a ListView
                            rssAdapter adapter = new rssAdapter(getActivity(), list2);
                            ListView listView = (ListView) getView().findViewById(R.id.list);
                            listView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss, container, false);
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
        Comm.send("RSS_GET$GET");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void addFeed(View view) {

        Comm.send("RSS_ADD$"+((EditText)getView().findViewById(R.id.editText3)).getText().toString());
    }

    private class rssAdapter extends ArrayAdapter<String> {
        public rssAdapter(Context context, ArrayList<String> list) {
            super(context, 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final String l = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_item, parent, false);
            }
            // Lookup view for data population
            TextView line = (TextView) convertView.findViewById(R.id.line);
            Button button = (Button) convertView.findViewById(R.id.remove);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    Log.d("wat","click");
                    Comm.send("RSS_REMOVE$" + l);
                }
            });
            // Populate the data into the template view using the data object
            line.setText(l);
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
