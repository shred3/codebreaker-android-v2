package edu.cnm.deepdive.codebreaker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.codebreaker.adapter.HistoryAdapter.Holder;
import edu.cnm.deepdive.codebreaker.databinding.ItemHistoryBinding;
import java.text.DateFormat;
import java.text.NumberFormat;

public class HistoryAdapter extends RecyclerView.Adapter<Holder> {

  private final LayoutInflater inflater;
  private final DateFormat dateFormat;
  private final DateFormat timeFormat;
  private final NumberFormat numberFormat;
  private final String startFormat;
  private final String attemptsFormat;
  private final String elapsedTimeFormat;

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class Holder extends RecyclerView.ViewHolder {

    private final ItemHistoryBinding binding;

    private Holder(ItemHistoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      // TODO Set contents of text views, based on GameWithGuesses at position.
    }

  }

}
