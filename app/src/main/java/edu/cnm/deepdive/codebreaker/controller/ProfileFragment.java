package edu.cnm.deepdive.codebreaker.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.FragmentProfileBinding;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.viewmodel.UserViewModel;

public class ProfileFragment extends DialogFragment implements TextWatcher {

  private View root;
  private AlertDialog dialog;
  private FragmentProfileBinding binding;
  private UserViewModel viewModel;
  private User user;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO Read & process any arguments.
  }

  @SuppressWarnings("ConstantConditions")
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    binding = FragmentProfileBinding.inflate(LayoutInflater.from(getContext()), null, false);
    binding.displayName.addTextChangedListener(this);
    dialog = new AlertDialog.Builder(getContext())
        .setIcon(android.R.drawable.ic_dialog_info)
        .setTitle(R.string.profile_title)
        .setView(binding.getRoot())
        .setPositiveButton(android.R.string.ok, (dlg, wh) -> save())
        .setNeutralButton(android.R.string.cancel, (dlg, wh) -> { /* Do nothing */})
        .create();
    dialog.setOnShowListener((dlg) -> checkSubmitConditions());
    return dialog;
  }

  private void save() {
    user.setDisplayName(binding.displayName.getText().toString().trim());
    String interests = binding.interests.getText().toString().trim();
    user.setInterests(interests.isEmpty() ? null : interests);
    viewModel.save(user);
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
    viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
      this.user = user;
      binding.displayName.setText(user.getDisplayName());
      if (user.getInterests() != null) {
        binding.interests.setText(user.getInterests());
      }
    });
  }

  private void checkSubmitConditions() {
    Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    String displayName = binding.displayName.getText().toString().trim();
    positive.setEnabled(!displayName.isEmpty());
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    checkSubmitConditions();
  }
}
