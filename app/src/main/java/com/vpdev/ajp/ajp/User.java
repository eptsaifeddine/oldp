package com.vpdev.ajp.ajp;

/**
 * Created by saifeddine on 23/07/17.
 */

public class User  {

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre_sorties() {
        return nombre_sorties;
    }

    public String getDepenses() {
        return depenses;
    }

    public String getUser_name() {

        return user_name;
    }

   private String user_name  , phone_number,email,nombre_sorties,depenses ;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre_sorties(String nombre_sorties) {
        this.nombre_sorties = nombre_sorties;
    }

    public void setDepenses(String depenses) {
        this.depenses = depenses;
    }

    User (String user_name, String phone_number, String email, String nombre_sorties, String depenses)
    {
        this.phone_number=phone_number ;
        this.nombre_sorties=nombre_sorties ;
        this.depenses=depenses ;
        this.user_name=user_name ;
        this.email=email ;
    }


}
