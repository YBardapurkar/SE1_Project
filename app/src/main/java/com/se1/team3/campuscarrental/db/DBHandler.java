package com.se1.team3.campuscarrental.db;

import com.se1.team3.campuscarrental.models.SystemUser;

public interface DBHandler {
//    TODO - Amrutha - uncomment these and implement

   public SystemUser login(String username, String password);

    public void signUpUser(SystemUser systemUser);

//    other functions
}
