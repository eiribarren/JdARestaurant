package com.epumer.jdarestaurant;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VerReserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerReserva extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FECHA = "fecha";
    private static final String COMENSALES = "comensales";
    private static final String NOMBRE = "nombre";
    private static final String TELEFONO = "telefono";
    private static final String COMENTARIOS = "comentarios";

    // TODO: Rename and change types of parameters
    private String fecha, comensales, nombre, telefono, comentarios;

    public VerReserva() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment VerReserva.
     */
    // TODO: Rename and change types and number of parameters
    public static VerReserva newInstance(Reserva reserva) {
        VerReserva fragment = new VerReserva();
        Bundle args = new Bundle();
        args.putString(FECHA, reserva.getFecha());
        args.putString(COMENSALES, reserva.getComensales());
        args.putString(NOMBRE, reserva.getNombre());
        args.putString(TELEFONO, reserva.getTelefono());
        args.putString(COMENTARIOS, reserva.getComentarios());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fecha = getArguments().getString(FECHA);
            comensales = getArguments().getString(COMENSALES);
            nombre = getArguments().getString(NOMBRE);
            telefono = getArguments().getString(TELEFONO);
            comentarios = getArguments().getString(COMENTARIOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ver_reserva, container, false);

        TextView fechaView, comensalesView, nombreView, telefonoView, comentariosView;
        fechaView = v.findViewById(R.id.fechaVer);
        comensalesView = v.findViewById(R.id.comensalesVer);
        nombreView = v.findViewById(R.id.nombreVer);
        telefonoView = v.findViewById(R.id.telefonoVer);
        comentariosView = v.findViewById(R.id.comentariosVer);

        fechaView.setText(fecha);
        comensalesView.setText(comensales);
        nombreView.setText(nombre);
        telefonoView.setText(telefono);
        comentariosView.setText(comentarios);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}
