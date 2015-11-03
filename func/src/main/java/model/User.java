package model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USERLIST")
public class User {
    @Id
    @SequenceGenerator(name = "userlist_seq_gen", sequenceName = "userlist_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userlist_seq_gen")
    private Integer id;
    @Column
    private String login;
    @Column
    private String pass;

    private Set<Tradition> traditions;

    public User() {}

    public User(String pass, String login) {
        //this.data = data;
        this.pass = pass;
        this.login = login;
    }

    //private UserData data;
    //TODO add userData.

    public User(Integer id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public Integer getId(){
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @JoinColumn(columnDefinition = "id", referencedColumnName = "USER_ID")
    public Set<Tradition> getTraditions() {
        return traditions;
    }

    public void setTraditions(Set<Tradition> traditions) {
        this.traditions = traditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null)
                && !(login != null ? !login.equals(user.login) : user.login != null)
                && !(pass != null ? !pass.equals(user.pass) : user.pass != null);

    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
