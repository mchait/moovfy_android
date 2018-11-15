package com.moovfy.moovfy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerListChats;
    private ListCloseAdapter adapter;
    List<Usuario> userList;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        setTitle("Chats");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerListChats = (RecyclerView) findViewById(R.id.recycleListChats);
        recyclerListChats.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
        adapter = new ListCloseAdapter(this, userList, new ListCloseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Usuario user) {
                Log.d("Listener Activat","Click en l'usuari" + user.getUsername());
            }
        });
        recyclerListChats.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {

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

    @NonNull
    @Override
    public ItemChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view =  inflater.inflate(R.layout.item_friend, viewGroup,false);
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

        public ItemChatViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
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