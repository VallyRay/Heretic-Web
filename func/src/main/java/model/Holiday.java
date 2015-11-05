package model;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
//@Indexed
public class Holiday implements Resource, Comparable<Holiday> {
    @Id
    @SequenceGenerator(name = "holiday_seq_gen", sequenceName = "holiday_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_seq_gen")
    private int id;
    @Column(name = "NAME")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    private HolidayType type;
    private Set<Tradition> traditions;

    public Holiday() {
    }

    public Holiday(String name) {
        this.name = name;
        this.startDate = new Date();
        this.type = HolidayType.OTHER;
    }

    public Holiday(String name, String type) {
        this.name = name;
        this.startDate = new Date();
        this.type = HolidayType.valueOf(type);
    }

    public Holiday(String name, Date start, String typeNum) {
        this.name = name;
        this.startDate = start;
        this.type = HolidayType.valueOf(typeNum);
    }

    public Holiday(Integer id, String name, Date startDate,
                   String typeNum) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.type = HolidayType.valueOf(typeNum);
    }

    public Holiday(String name, Date startDate, Date endDate,
                   String typeNum) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = HolidayType.valueOf(typeNum);
    }

    public Holiday(Integer id, String name, Date startDate,
                   Date endDate, String typeNum) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = HolidayType.valueOf(typeNum);
    }

    public Set<Tradition> getTraditions() {
        return traditions;
    }

    public void setTraditions(Set<Tradition> traditions) {
        this.traditions = traditions;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        DateLabelFormatter formatter = new DateLabelFormatter();
        String s = formatter.dateFormat(startDate);

        return String.format("%30s%15s%15s", name, s, type);
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.startDate;
    }

    @Column(name = "TYPE_ID")
    @Enumerated(EnumType.ORDINAL)
    public HolidayType getType() {
        return this.type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Holiday holiday = (Holiday) o;

        return name.equals(holiday.name)
                && startDate.equals(holiday.startDate)
                && type == holiday.type;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public int compareTo(Holiday holiday) {
        if (holiday == null) return -1;

        if (holiday.getName().charAt(0) > this.getName().charAt(0)) return -1;
        else if (holiday.getName().charAt(0) < this.getName().charAt(0)) return 1;
        return 0;
    }
}