package com.bild4.mror;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import java.sql.Time;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlindsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlindsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlindsFragment extends Fragment {
    Handler handler;
    private OnFragmentInteractionListener mListener;

    public BlindsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlindsFragment newInstance() {
        BlindsFragment fragment = new BlindsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());

        Comm.getSocket().addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket websocket, final String message) throws Exception {

                if (message.startsWith("BLINDS")) {
                    handler.post(new Runnable() {
                        public void run() {
//                            Log.d("recieved", message);

                            if (message.contains("true")) {
//                                ((TextView) getView().findViewById(R.id.textView3)).setVisibility(View.VISIBLE);
                            } else if (message.contains("false")) {
//                                ((TextView) getView().findViewById(R.id.textView3)).setVisibility(View.GONE);

                            }
                        }
                    });
                }
            }
        });
        Comm.send("BLINDS$get");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_blinds, container, false);
        final ToggleButton toggle = (ToggleButton) ll.findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Comm.send("BLINDS$auto");
                } else {
                    int n = ((SeekBar) getView().findViewById(R.id.seekBar)).getProgress();
                    Comm.send("BLINDS$" + String.valueOf(n));
                }
            }
        });

        final TextView vv = (TextView) ll.findViewById(R.id.leftTime);
        vv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = new Time(System.currentTimeMillis()).getHours();
                int minute = new Time(System.currentTimeMillis()).getMinutes();
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(vv.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        vv.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        final TextView cc = (TextView) ll.findViewById(R.id.rightTime);
        cc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = new Time(System.currentTimeMillis()).getHours();
                int minute = new Time(System.currentTimeMillis()).getMinutes();
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(cc.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        cc.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        return ll;
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
     * to the acactivitytivity and potentially other fragments contained in that
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


    public void blindsSend(View v) {
        final ToggleButton toggle = (ToggleButton) getView().findViewById(R.id.toggleButton);
        toggle.setChecked(false);
        int n = ((SeekBar) getView().findViewById(R.id.seekBar)).getProgress();
        Comm.send("BLINDS$" + String.valueOf(n));
    }

    public void socketon(View v) {

        Comm.send("SOCKET$1");
    }

    public void socketoff(View v) {

        Comm.send("SOCKET$0");
    }
}
