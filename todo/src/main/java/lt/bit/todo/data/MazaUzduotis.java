
package lt.bit.todo.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mazos_uzduotys")
public class MazaUzduotis implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "uzduotis_id", referencedColumnName = "id")
    private Uzduotis uzduotis;
    
    @Column(nullable = false)
    private String pavadinimas;
     
    private String aprasymas;
    
    @Temporal(TemporalType.DATE)
    private Date atlikta;

    public MazaUzduotis() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uzduotis getUzduotis() {
        return uzduotis;
    }

    public void setUzduotis(Uzduotis uzduotis) {
        this.uzduotis = uzduotis;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public Date getAtlikta() {
        return atlikta;
    }

    public void setAtlikta(Date atlikta) {
        this.atlikta = atlikta;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MazaUzduotis other = (MazaUzduotis) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MazaUzduotis{" + "id=" + id + ", uzduotis=" + uzduotis + ", pavadinimas=" + pavadinimas + ", aprasymas=" + aprasymas + ", atlikta=" + atlikta + '}';
    }
    
    
    
}
