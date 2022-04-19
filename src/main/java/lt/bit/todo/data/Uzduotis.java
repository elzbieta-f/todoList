package lt.bit.todo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "uzduotys")
public class Uzduotis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "vartotojas_id", referencedColumnName = "id")
    private Vartotojas vartotojas;

    @Column(nullable = false)
    private String pavadinimas;

    private String aprasymas;

    @Column(name = "iki_kada")
    @Temporal(TemporalType.DATE)
    private Date ikiKada;

    @Column(nullable = false)
    private Integer statusas;

    @Temporal(TemporalType.DATE)
    private Date atlikta;

    @OneToMany(mappedBy = "uzduotis")
    private List<MazaUzduotis> mazosUzduotys;

    public Uzduotis() {
        this.statusas = 0;
        this.mazosUzduotys = new ArrayList();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Vartotojas getVartotojas() {
        return vartotojas;
    }

    public void setVartotojas(Vartotojas vartotojas) {
        this.vartotojas = vartotojas;
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

    public Date getIkiKada() {
        return ikiKada;
    }

    public void setIkiKada(Date ikiKada) {
        this.ikiKada = ikiKada;
    }

    public Integer getStatusas() {
        return statusas;
    }

    public void setStatusas(Integer statusas) {
        if (statusas == null) {
            this.statusas = null;
        } else if (statusas < 0) {
            this.statusas = 0;
        } else if (statusas > 100) {
            this.statusas = 100;
        } else {
            this.statusas = statusas;
        }
    }

    public Date getAtlikta() {
        return atlikta;
    }

    public void setAtlikta(Date atlikta) {
        if (atlikta!=null){
            this.statusas=100;
        } else {
            if (this.atlikta!=null){
                this.statusas=0;
            }
        }
        this.atlikta = atlikta;
    }

    public List<MazaUzduotis> getMazosUzduotys() {
        return mazosUzduotys;
    }

    public void setMazosUzduotys(List<MazaUzduotis> mazosUzduotys) {
        this.mazosUzduotys = mazosUzduotys;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.Id);
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
        final Uzduotis other = (Uzduotis) obj;
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uzduotis{" + "Id=" + Id + ", vartotojas=" + vartotojas + ", pavadinimas=" + pavadinimas + ", aprasymas=" + aprasymas + ", ikiKada=" + ikiKada + ", statusas=" + statusas + ", atlikta=" + atlikta + '}';
    }

}
