package tudienav.laptrinhandroid.nhom7.tudien;

/**
 * Created by Fusion on 13/04/2016.
 */
public class WordObject {

    private String eng;
    private String kind;
    private String pronounce;
    private String vie;
    private int userCheck;
    public WordObject(String eng, String kind, String pronounce, String vie) {
        this.eng = eng;
        this.kind = kind;
        this.pronounce = pronounce;
        this.vie = vie;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getVie() {
        return vie;
    }

    public void setVie(String vie) {
        this.vie = vie;
    }

    public int getuserCheck() {
        return userCheck;
    }

    public void setUserCheck(int userCheck) {
        this.userCheck = userCheck;
    }
}
