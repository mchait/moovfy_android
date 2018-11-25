package com.moovfy.moovfy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity implements RecyclerItemTouchHelperChats.RecyclerItemTouchHelperChatsListener {

    private RecyclerView recyclerListChats;
    private ListChatsAdapter adapter;
    List<Usuario> userList;
    private RelativeLayout relativeLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        setTitle("Chats");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerListChats = (RecyclerView) findViewById(R.id.recycleListChats);
        recyclerListChats.setLayoutManager(linearLayoutManager);

        relativeLayout = findViewById(R.id.relative_layout_cahts);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperChats(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerListChats);


        userList = new ArrayList<>();
        userList.add(new Usuario(
                "Usuario1",
                "Ultim missatge user 1",
                R.drawable.icono
        ));
        userList.add(new Usuario(
                "Usuario2",
                "Ultim missatge user 2",
                R.drawable.icono
        ));
        userList.add(new Usuario(
                "Usuario3",
                "Ultim missatge user 3",
                R.drawable.icono
        ));
        userList.add(new Usuario(
                "Usuario4",
                "Descripcion del user4",
                R.drawable.icono
        ));
        userList.add(new Usuario(
                "Usuario5",
                "Descripcion del user5",
                R.drawable.icono
        ));
        userList.add(new Usuario(
                "Usuario6",
                "Descripcion del user6",
                R.drawable.icono
        ));
        adapter = new ListChatsAdapter(this, userList, new ListChatsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Usuario user) {
                Log.d("Listener Activat","Click en l'usuari" + user.getUsername());
            }
        });
        recyclerListChats.setAdapter(adapter);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ListChatsAdapter.ItemChatViewHolder) {
            // get the removed item name to display it in snack bar
            String name = userList.get(viewHolder.getAdapterPosition()).getUsername();

            // backup of removed item for undo purpose
            final Usuario deletedItem = userList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, name + " removed from black list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}

class ListChatsAdapter extends RecyclerView.Adapter<ListChatsAdapter.ItemChatViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Usuario user);
    }

    private Context mCtx;
    private List<Usuario> userList;
    private final OnItemClickListener listener;

    public ListChatsAdapter(Context mCtx, List<Usuario> userList, OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.userList = userList;
        this.listener = listener;
    }

    public void removeItem(int position) {
        userList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Usuario item, int position) {
        userList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public ItemChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view =  inflater.inflate(R.layout.item_chat, viewGroup,false);
        return new ItemChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemChatViewHolder itemCloseViewHolder, int i) {

        itemCloseViewHolder.bind(userList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class ItemChatViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUsername, textViewDesc;
        ImageView imageView;

        public RelativeLayout viewBackground, viewForeground;

        public ItemChatViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewDesc = itemView.findViewById(R.id.textViewLastMsg);
            imageView = itemView.findViewById(R.id.imageView);

            viewBackground = itemView.findViewById(R.id.view_background_chats);
            viewForeground = itemView.findViewById(R.id.view_foreground_chats);
        }

        public void bind(final Usuario user, final OnItemClickListener listener) {
            textViewUsername.setText(user.getUsername());
            textViewDesc.setText(user.getDesc());
            imageView.setImageDrawable(mCtx.getResources().getDrawable(user.getIcon()));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });
        }
    }
}