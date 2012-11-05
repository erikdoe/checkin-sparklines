package com.thoughtworks.scmviz.model;

public class Repository
{
    String url;
    String username;
    String password;

    public Repository(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
         String[] pathElements = url.split("/");
         String name = pathElements[pathElements.length - 1];
         if (name.equals("trunk")) {
             name = pathElements[pathElements.length - 2];
         }
         return name;
     }

}
