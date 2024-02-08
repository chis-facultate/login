package org.example.model;

public class CredentialeLogin {
    private String email;
    private String parola;

    public CredentialeLogin() {
    }

    public CredentialeLogin(String email, String parola) {
        this.email = email;
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public String getParola() {
        return parola;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
