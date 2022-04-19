package lt.bit.todo.data;

import com.password4j.Password;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

@Entity
@Table(name = "vartotojai")
public class Vartotojas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String vardas;

    @Column(nullable = false)
    private String slaptazodis;

    @Column(nullable = false)
    private Boolean admin;

//    private String salt;

//    @OneToMany(mappedBy = "vartotojas")
//    List<Uzduotis> uzduotys;
    public Vartotojas() {
        this.admin = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

//    public String getDecodedSlaptazodis(){
//        if (this.slaptazodis==null){
//            return null;
//        }
//        String decoded ="";
//        for (int i = 0; i < this.slaptazodis.length(); i++) {
//            char c=this.slaptazodis.charAt(i);
//            c-=4;
//            decoded+=c;
//        }
//        return decoded;
//    }
//    public void setDecodedSlaptazodis(String slaptazodis){
//        if (slaptazodis==null){
//            this.slaptazodis=null;
//        }
//        String encoded ="";
//        for (int i = 0; i < slaptazodis.length(); i++) {
//            char c=slaptazodis.charAt(i);
//            c+=4;
//            encoded+=c;
//        }
//        this.slaptazodis=encoded;
//    }
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Vartotojas other = (Vartotojas) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vartotojas{" + "id=" + id + ", vardas=" + vardas + ", slaptazodis=" + slaptazodis + ", admin=" + admin + '}';
    }

//    public static String encodePassword(String password){
//        if (password == null) {
//            return null;
//        } else {
//            String encoded = null;
//            try {
//                MessageDigest md = MessageDigest.getInstance("MD5");
//                md.update(password.getBytes());
//                byte[] digest = md.digest();
//                encoded = DatatypeConverter.printHexBinary(digest).toLowerCase();
//            } catch (NoSuchAlgorithmException ex) {
//                System.err.println("Nepavyko uzsifruoti:");
//                System.err.println(ex);
//            }
//            return encoded;
//        }
//
//    }
//    public static String encodePassword(String password) {
//        if (password == null) {
//            return null;
//        } else {
//            String encoded = null;
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA512");
//                md.update(password.getBytes());
//                byte[] digest = md.digest();
//                encoded = DatatypeConverter.printHexBinary(digest).toLowerCase();
//            } catch (NoSuchAlgorithmException ex) {
//                System.err.println("Nepavyko uzsifruoti:");
//                System.err.println(ex);
//            }
//            return encoded;
//        }
//
//    }
//
//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
//
//    public static String randomString(int min, int max) {
//        if (min<0||min>200){
//            min=10;
//        } 
//        if (max<0||min>200){
//            max=20;
//        }
//        if (min>max){
//            int tmp=min;
//            min=max;
//            max=tmp;
//        }
//        int len = (int) (Math.random() * min + max);
//        String r = "";
//        for (int i = 0; i < len; i++) {
//            char c= (char) (Math.random() * 25 + 97);
//            r +=c;
//            
//        }
//        return r;
//    }
//
//    public static String encodePassword(String password, String salt) {
//            if (password == null) {
//            return null;
//        } else {
//            String encoded = null;
//            try {
//                String withSalt = password + salt;
//                MessageDigest md = MessageDigest.getInstance("SHA512");
//                md.update(withSalt.getBytes());
//                byte[] digest = md.digest();
//                encoded = DatatypeConverter.printHexBinary(digest).toLowerCase();
//            } catch (NoSuchAlgorithmException ex) {
//                System.err.println("Nepavyko uzsifruoti:");
//                System.err.println(ex);
//            }
//            return encoded;
//        }
//
//    }
    
    public static String encodeNewPassword(String password) {
        return Password
                .hash(password)
                .addRandomSalt()
                .withArgon2()
                .getResult();
    }
    
    public static boolean checkPassword(String password, String realPassword) {
        System.out.println(password);
        System.out.println(realPassword);
        return Password
                .check(password, realPassword)
                .withArgon2();
    }
}
