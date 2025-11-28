// app/src/main/java/kr/ac/dankook/autoinfo/adapters/ChatAdapter.java
package kr.ac.dankook.autoinfo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.models.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VH> {
    private List<Chat> items;
    public ChatAdapter(List<Chat> items){ this.items = items; }
    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new VH(v);
    }
    @Override public void onBindViewHolder(VH holder, int position){
        Chat c = items.get(position);
        holder.message.setText(c.getMessage());
        holder.sender.setText(c.getSenderId());
    }
    @Override public int getItemCount(){ return items==null?0:items.size(); }
    static class VH extends RecyclerView.ViewHolder{
        TextView message, sender;
        VH(View itemView){
            super(itemView);
            message = itemView.findViewById(R.id.tv_chat_message);
            sender = itemView.findViewById(R.id.tv_chat_sender);
        }
    }
}