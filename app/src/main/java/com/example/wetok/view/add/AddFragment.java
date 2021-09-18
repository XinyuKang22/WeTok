package com.example.wetok.view.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wetok.R;

import java.util.Random;

public class AddFragment extends Fragment {

//    private AddViewModel addViewModel;
//    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_add, container, false);
        final TextView textView = view.findViewById(R.id.text_add);
        textView.setText("abc" + new Random().nextInt());
//        addViewModel =
//                new ViewModelProvider(this).get(AddViewModel.class);
//
//        binding = FragmentAddBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textAdd;
//        addViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        System.out.println(1);
//        return root;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}