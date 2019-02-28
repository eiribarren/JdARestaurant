package com.epumer.jdarestaurant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HacerReservaListener} interface
 * to handle interaction events.
 * Use the {@link HacerReserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HacerReserva extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private HacerReservaListener mListener;

    public HacerReserva() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HacerReserva.
     */
    // TODO: Rename and change types and number of parameters
    public static HacerReserva newInstance(String param1, String param2) {
        HacerReserva fragment = new HacerReserva();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hacer_reserva, container, false);

        final EditText fecha, comensales, nombre, telefono, comentarios;
        Button enviarButton;
        fecha = v.findViewById(R.id.fechaInput);
        comensales = v.findViewById(R.id.comensalesInput);
        nombre = v.findViewById(R.id.nombreInput);
        telefono = v.findViewById(R.id.telefonoInput);
        comentarios = v.findViewById(R.id.comentariosInput);

        enviarButton = v.findViewById(R.id.enviarButton);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("EPUMERTAG", "hey");
                mListener.hacerReserva(new Reserva(fecha.getText().toString(),
                                                   comensales.getText().toString(),
                                                   nombre.getText().toString(),
                                                   telefono.getText().toString(),
                                                   comentarios.getText().toString()));
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HacerReservaListener) {
            mListener = (HacerReservaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HacerReservaListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface HacerReservaListener {
        // TODO: Update argument type and name
        void hacerReserva(Reserva reserva);
    }
}
