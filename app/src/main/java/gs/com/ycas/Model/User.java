package gs.com.ycas.Model;

import com.google.firebase.database.FirebaseDatabase;

public class User {
    public String uid, email, name, contact, language, type;

    public User(User user){
        this.uid = user.uid;
        this.email = user.email;
        this.name = user.name;
        this.contact = user.contact;
        this.language = user.language;
        this.type = user.type;
    }

    public User(){
        this.uid = "";
        this.email = "";
        this.name = "";
        this.contact = "";
        this.language = "";
        this.type = "";
    }

    public User(String uid, String email){
        this.uid = uid;
        this.email = email;
    }

    public User(String uid, String email, String name, String contact, String language, String type){
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.language = language;
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUid() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public String getContact() {
        return this.contact;
    }

    public String getLanguage() {
        return language;
    }

    public String getType() {
        return type;
    }


    // [START basic_write]
    public void writeNewUser() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(this.uid).setValue(this);
    }
    // [END basic_write]
}