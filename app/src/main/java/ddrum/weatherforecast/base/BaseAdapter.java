package ddrum.weatherforecast.base;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ddrum.weatherforecast.databinding.ItemCurrentBinding;


public abstract class BaseAdapter<M, VH extends BaseViewHolder, B extends ViewDataBinding>
        extends RecyclerView.Adapter<VH> {
    private List<M> list;
    public Context context;


    public BaseAdapter(Context context) {
        this.context = context;
    }

    protected abstract int getLayout();

    protected abstract VH getViewHolder(B binding);

    protected abstract void bindView(M item, VH holder, int position);

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                getLayout(), parent, false);
        return getViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        bindView(list.get(position), holder, position);
    }

    @Override
    public int getItemCount() {
        if (list !=null){
            return list.size();
        }
        return 0;
    }

    public void updateData(List<M> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(M item) {
        this.list.add(item);
        notifyItemChanged(list.size() - 1);
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(M item) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(item)) {
                removeItem(i);
                break;//ok oke
            }
        }
    }
}
