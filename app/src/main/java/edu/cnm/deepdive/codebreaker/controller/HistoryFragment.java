package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.codebreaker.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

  private FragmentHistoryBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHistoryBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

}