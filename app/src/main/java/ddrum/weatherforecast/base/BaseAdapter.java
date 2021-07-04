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

import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.databinding.ItemCurrentBinding;


public abstract class BaseAdapter<M, VH extends BaseViewHolder<B>, B extends ViewDataBinding>
        extends RecyclerView.Adapter<VH> {

    protected List<M> list = new ArrayList<>();
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
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void updateData(List<M> list) {
//        this.list.clear();
//        this.list.addAll(list);
        this.list = list;
        notifyDataSetChanged();
    }
   public void clearData(){
        this.list.clear();
        notifyDataSetChanged();
   }

}
