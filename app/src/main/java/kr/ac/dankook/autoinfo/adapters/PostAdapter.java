package kr.ac.dankook.autoinfo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.VH> {
    private List<Post> items;
    public PostAdapter(List<Post> items){ this.items = items; }
    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new VH(v);
    }
    @Override public void onBindViewHolder(VH holder, int position){
        Post p = items.get(position);
        holder.title.setText(p.getTitle());
        holder.price.setText(String.valueOf(p.getPrice()));
    }
    @Override public int getItemCount(){ return items==null?0:items.size(); }
    static class VH extends RecyclerView.ViewHolder{
        TextView title, price;
        VH(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.tv_post_title);
            price = itemView.findViewById(R.id.tv_post_price);
        }
    }
}