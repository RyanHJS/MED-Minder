package com.example.medminder.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medminder.R;
import com.example.medminder.bean.myListBean;
import java.util.List;

public class myAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<myListBean> mDatas;
    private AdapterListener adapterListener;
    public myAdapter(Context context,List<myListBean> mDatas,AdapterListener adapterListener) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.adapterListener = adapterListener;
    }
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 :  mDatas.size();
    }

    public void updata(List<myListBean> mDatas){
        this.mDatas = mDatas;
        for (myListBean bean: this.mDatas)
            System.out.println(bean.toString());
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.items
                , parent, false);
        return new MyHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myListBean bean = mDatas.get(position);

        myHolder .checkBox.setChecked(bean.isCheck());
        System.out.println(bean.isCheck());
        myHolder.title.setText(bean.getTitle());
        myHolder .checkBox.setOnClickListener(v -> {
            adapterListener.CheckedChanged(position,bean.isCheck());
        });
        myHolder.title.setOnClickListener(v -> {
            adapterListener.OnClickTitle(bean.getTitle());
        });
        myHolder .checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myHolder .checkBox.setChecked(!isChecked);
            }
        });

    }
    public class MyHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private CheckBox checkBox;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public interface AdapterListener{
        void OnClickTitle(String title);
        void CheckedChanged(int position,boolean isChecked);
    }
}
