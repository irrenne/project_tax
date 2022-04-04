package com.epam.tax.entities;

import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;
    private long roleId;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static User createUser(String login, String name, String surname, String password) {
        User user = new User();
        user.login = login;
        user.name = name;
        user.surname = surname;
        user.password = password;
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, roleId);
    }

    @Override
    public String toString() {
        return id + " " + name + " " + surname + " " + login + " " + role;
    }
}

