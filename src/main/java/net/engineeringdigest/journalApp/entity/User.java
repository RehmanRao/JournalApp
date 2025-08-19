package net.engineeringdigest.journalApp.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data

public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef
    private ArrayList<JournalEntity> journalEntries = new ArrayList<>();
    private List<String> roles;


    public ArrayList<JournalEntity> getJournalEntries() {
        return journalEntries;
    }

    public User(ObjectId id, @NonNull String userName, @NonNull String password, ArrayList<JournalEntity> journalEntries, ArrayList<String> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.journalEntries = journalEntries;
        this.roles = roles;
    }

    public void setJournalEntries(ArrayList<JournalEntity> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public  User(){

    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
