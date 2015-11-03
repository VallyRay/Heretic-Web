package model;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
//TODO add full text search (need update hibernate to support lucene search 4.2)
//@Indexed
//@AnalyzerDef(name="countryAnalyzer",
//tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
//filters = {
//        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
//                @Parameter(name = "language", value = "English")
//        })
//})

public class Country implements Serializable, Resource, Comparable<Country> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "country_seq_gen")
    @SequenceGenerator(name = "country_seq_gen", sequenceName = "country_seq")
    private Integer id;
    @Column
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;

    private Set<Tradition> traditions;

    public Country() {

    }

    public Country(String name) {
        this.name = name;
    }

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return name.equals(country.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(columnDefinition = "id", referencedColumnName = "COUNTRY_ID")
    public Set<Tradition> getTraditions() {
        return traditions;
    }

    public void setTraditions(Set<Tradition> traditions) {
        this.traditions = traditions;
    }

    @Override
    public int compareTo(Country country) {

        if (country == null) return -1;

        if (this.getName().charAt(0) > country.getName().charAt(0)) return 1;
        else if (this.getName().charAt(0) < country.getName().charAt(0)) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return this.name;
    }
}