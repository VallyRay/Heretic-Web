package model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Indexed
public class Tradition implements Resource {

    @Id
    private Integer id;

    private Holiday holiday;

    private Country country;

    private String description;

    private User user;

    private Set<Comment> comments;

    public Tradition() {

    }

    public Tradition(Holiday holiday, Country country) {
        this.holiday = holiday;
        this.country = country;
        this.description = "";
    }

    public Tradition(Holiday holiday, Country country, String description) {
        this.holiday = holiday;
        this.country = country;
        this.description = description;
    }

    public Tradition(Holiday holiday, Country country, String description, User user) {
        this.holiday = holiday;
        this.country = country;
        this.description = description;
        this.user = user;
    }

    public Tradition(Integer id, Holiday holiday, Country country,
                     String description) {
        this.id = id;
        this.holiday = holiday;
        this.country = country;
        this.description = description;
    }

    @JoinColumn(columnDefinition = "id", referencedColumnName = "TRADITION_ID")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.description;
    }

    @IndexedEmbedded
    @JoinColumn(name = "holiday_id", referencedColumnName = "id")
    public Holiday getHoliday() {

        return this.holiday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tradition tradition = (Tradition) o;

        return country.equals(tradition.country) && description.equals(tradition.description) && holiday.equals(tradition.holiday);

    }

    @Override
    public int hashCode() {
        int result = holiday.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    public void setId(int id){
        this.id = id;
    }

    @IndexedEmbedded
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    public Country getCountry() { return this.country;}

    public String toString() {
        return String.format("%30s in %20s",this.holiday.getName(),this.country.getName());
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}