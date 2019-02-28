package com.epumer.jdarestaurant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaReservasListener} interface
 * to handle interaction events.
 * Use the {@link ListaReservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaReservas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListaReservasListener mListener;

    public ListaReservas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaReservas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaReservas newInstance(String param1, String param2) {
        ListaReservas fragment = new ListaReservas();
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
        View v = inflater.inflate(R.layout.fragment_lista_reservas, container, false);

        RecyclerView rv = v.findViewById(R.id.reservasRecyclerView);
        ReservasAdapter adapter = new ReservasAdapter(mListener.getReservas());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListaReservasListener) {
            mListener = (ListaReservasListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListaReservasListener");
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
    public interface ListaReservasListener {
        // TODO: Update argument type and name
        List<Reserva> getReservas();
        void verReserva(Reserva reserva);
    }

    public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ReservaHolder> {

        List<Reserva> reservaList;

        public ReservasAdapter(List<Reserva> reservaList) {
            this.reservaList = reservaList;
        }

        @NonNull
        @Override
        public ReservaHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserva_holder, parent, false);

            ReservaHolder rh = new ReservaHolder(v);

            v.setOnClickListener(rh);

            return rh;
        }

        @Override
        public void onBindViewHolder(@NonNull ReservaHolder reservaHolder, int i) {
            reservaHolder.setReserva(reservaList.get(i));
            reservaHolder.fecha.setText("Fecha: "+reservaList.get(i).getFecha());
            reservaHolder.comensales.setText("Personas: "+reservaList.get(i).getComensales());
        }

        @Override
        public int getItemCount() {
            return reservaList.size();
        }

        public class ReservaHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

            TextView fecha, comensales;
            Reserva reserva;

            public ReservaHolder(@NonNull View itemView) {
                super(itemView);
                fecha = itemView.findViewById(R.id.fechaReserva);
                comensales = itemView.findViewById(R.id.personasReserva);
            }

            public void setReserva(Reserva r) {
                Log.i("EPUMERTAG", r.getNombre());
                this.reserva = r;
            }

            @Override
            public void onClick(View v) {
                mListener.verReserva(this.reserva);
            }
        }
    }
}
