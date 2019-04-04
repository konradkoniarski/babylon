package co.uk.babylon.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
