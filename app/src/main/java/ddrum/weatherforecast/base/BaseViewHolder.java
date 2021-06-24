package ddrum.weatherforecast.base;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public abstract class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public B binding;
    public BaseViewHolder(@NonNull @NotNull B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}