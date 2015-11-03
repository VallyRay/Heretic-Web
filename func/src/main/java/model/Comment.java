package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    private Integer id;
    @Column
    private String text;
    @JoinColumn(name = "TRADITION_ID", referencedColumnName = "ID")
    private Tradition tradition;
    //TODO add user.
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
    @Column(name = "CUR_DATE")
    private Date date;

    public Comment() {}
    public Comment(String text, Tradition tradition, User user,
            Date date) {
        this.text = text;
        this.tradition = tradition;
        this.user = user;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Tradition getTradition() {
        return tradition;
    }

    public void setTradition(Tradition tradition) {
        this.tradition = tradition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return !(id != null ? !id.equals(comment.id) : comment.id != null)
                && !(text != null ? !text.equals(comment.text) : comment.text != null)
                && !(tradition != null ? !tradition.equals(comment.tradition) : comment.tradition != null)
                && !(user != null ? !user.equals(comment.user) : comment.user != null)
                && !(date != null ? !date.equals(comment.date) : comment.date != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (tradition != null ? tradition.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", tradition=" + tradition +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
