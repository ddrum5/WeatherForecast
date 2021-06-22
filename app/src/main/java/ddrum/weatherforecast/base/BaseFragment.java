package ddrum.weatherforecast.base;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import ddrum.weatherforecast.Ultis.Ulti;

public abstract class BaseFragment<VM extends BaseViewModel, B extends ViewDataBinding> extends Fragment {

    protected abstract int getLayout();
    protected abstract VM getViewModel();

    protected abstract void initView(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState);
    protected void initObserve() {}

    protected B binding;
    protected VM viewModel;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = (VM) new ViewModelProvider(requireActivity()).get(getViewModel().getClass());
        initView(view, savedInstanceState);
        initObserve();
    }

    protected void navigateTo(@IdRes int actionId) {
        NavHostFragment.findNavController(this).navigate(actionId);
    }

    protected void popBackStack() {
        NavHostFragment.findNavController(this).popBackStack();
    }

    protected String convertTimestamp(int timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format((long)timestamp* 1000L);
    }

    protected  String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(System.currentTimeMillis());
    }
    protected void shortSnackBar(String text){
        Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT).show();
    }
    protected void longSnackBar(String text){
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).show();
    }


}
