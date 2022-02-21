package com.example.InsideBudget.Listener;

import java.util.ArrayList;
import com.example.InsideBudget.Modelo.User;

//Listener para a lista de utilizadores no admin
public interface UsersListener {

    void onRefreshListaUsers(ArrayList<User> listaUsers);
    void onErroListUsers(String message);
}
