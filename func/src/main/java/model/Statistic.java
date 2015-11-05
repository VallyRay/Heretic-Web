package model;

import javax.persistence.*;

@Entity
@Table
public class Statistic{
    @Id
    private Integer id;
    @Column
    private Integer reads;
    @JoinColumn(name = "TRADITION_ID", referencedColumnName = "ID")
    private Tradition tradition;

    public Statistic() {}

    public Statistic(Integer id, Integer reads,
                     Tradition tradition) {
        this.id = id;
        this.reads = reads;
        this.tradition = tradition;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public void setTradition(Tradition tradition) {
        this.tradition = tradition;
    }

    public Integer getId() {
        return id;
    }

    public Integer getReads() {
        return reads;
    }

    public Tradition getTradition() {
        return tradition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistic statistic = (Statistic) o;

        if (id != null ? !id.equals(statistic.id) : statistic.id != null) return false;
        if (reads != null ? !reads.equals(statistic.reads) : statistic.reads != null) return false;
        return !(tradition != null ? !tradition.equals(statistic.tradition) : statistic.tradition != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reads != null ? reads.hashCode() : 0);
        result = 31 * result + (tradition != null ? tradition.hashCode() : 0);
        return result;
    }
}
