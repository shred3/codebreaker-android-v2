package edu.cnm.deepdive.codebreaker.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.FragmentProfileBinding;

public class ProfileFragment extends DialogFragment {

  private View root;
  private AlertDialog dialog;
  private FragmentProfileBinding binding;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO Read & process any arguments.
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    binding = FragmentProfileBinding.inflate(LayoutInflater.from(getContext()), null, false);
    //noinspection ConstantConditions
    dialog = new AlertDialog.Builder(getContext())
        .setIcon(android.R.drawable.ic_dialog_info)
        .setTitle(R.string.profile_title)
        .setView(binding.getRoot())
        .setPositiveButton(android.R.string.ok, (dlg, wh) -> { /* TODO Save user info */ })
        .setNeutralButton(android.R.string.cancel, (dlg, wh) -> { /* Do nothing */})
        .create();
    dialog.setOnShowListener((dlg) -> { /* TODO invoke method to check submission conditions. */});
    return dialog;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // TODO Attach to & observe viewmodel.
  }

}
