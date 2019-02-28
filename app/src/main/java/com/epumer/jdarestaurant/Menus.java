package com.epumer.jdarestaurant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuListener} interface
 * to handle interaction events.
 * Use the {@link Menus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MenuListener mListener;

    public Menus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menus.
     */
    // TODO: Rename and change types and number of parameters
    public static Menus newInstance(String param1, String param2) {
        Menus fragment = new Menus();
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
        View v = inflater.inflate(R.layout.fragment_menus, container, false);

        RecyclerView rv = v.findViewById(R.id.listaDeMenus);
        List<Plato> platoList = mListener.getMenuList();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        MenusAdapter adapter = new MenusAdapter(platoList);
        rv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuListener) {
            mListener = (MenuListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MenuListener");
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
    public interface MenuListener {
        // TODO: Update argument type and name
        List<Plato> getMenuList();
    }

    public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.MenuViewHolder> {

        List<Plato> platoList;

        public MenusAdapter(List<Plato> platoList) {
            this.platoList = platoList;
        }

        @NonNull
        @Override
        public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_holder, parent, false);

            MenuViewHolder mvh = new MenuViewHolder(v);

            return mvh;
        }

        @Override
        public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
            menuViewHolder.nombre.setText(platoList.get(i).getNombre());
            menuViewHolder.descripcion.setText(platoList.get(i).getDescripcion());
            menuViewHolder.precio.setText("Precio: "+String.valueOf(platoList.get(i).getPrecio()));
        }

        @Override
        public int getItemCount() {
            return platoList.size();
        }

        public class MenuViewHolder extends RecyclerView.ViewHolder {

            TextView nombre, descripcion, precio;

            public MenuViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombreMenu);
                descripcion = itemView.findViewById(R.id.descripcionMenu);
                precio = itemView.findViewById(R.id.precioMenu);
            }
        }
    }
}
