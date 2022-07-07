package com.example.medminder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.medminder.R;
import com.example.medminder.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener
{

    ListView listViewData;
    ArrayAdapter<String> adapter;


    private FragmentHomeBinding binding;
@Nullable
@Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
       /* HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        *//*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*//*
        listViewData= getView().findViewById(R.id.listView_data);
        adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice,MedicationArray);
        listViewData.setAdapter(adapter);
        return root;*/

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }
   /* @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        String[] MedicationArray={"medication1","medication2","medication3","medication4","medication5","medication6"};
        ListView listView=(ListView)view.findViewById(R.id.listView_data);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,MedicationArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getActivity().getMenuInflater().inflate(R.menu.main_menu,menu);
        //super.onCreateOptionsMenu(menu,inflater);
        return true;



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id= item.getItemId();
        if(id==R.id.item_done)
        {
            String itemSelected="Selected items: \n";
            for(int i=0;i<listViewData.getCount();i++)
            {
                if(listViewData.isItemChecked(i))
                {
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                }
            }
            Toast.makeText(getActivity(),itemSelected,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        if(position==0)
            Toast.makeText(getActivity(),"medication1",Toast.LENGTH_SHORT).show();
        if(position==1)
            Toast.makeText(getActivity(),"medication2",Toast.LENGTH_SHORT).show();
        if(position==2)
            Toast.makeText(getActivity(),"medication3",Toast.LENGTH_SHORT).show();
        if(position==3)
            Toast.makeText(getActivity(),"medication4",Toast.LENGTH_SHORT).show();
        if(position==4)
            Toast.makeText(getActivity(),"medication5",Toast.LENGTH_SHORT).show();
        if(position==5)
            Toast.makeText(getActivity(),"medication6",Toast.LENGTH_SHORT).show();
    }


}