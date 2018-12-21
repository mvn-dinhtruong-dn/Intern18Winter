package asiantech.internship.summer.models;

public class Company {
    private int mIdCompany;
    private String mNameCompany;

    public Company(int mIdCompany, String mNameCompany) {
        this.mIdCompany = mIdCompany;
        this.mNameCompany = mNameCompany;
    }

    public Company(String mNameCompany) {
        this.mNameCompany = mNameCompany;
    }

    public Company() {
    }

    public int getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(int idCompany) {
        this.mIdCompany = idCompany;
    }

    public String getNameCompany() {
        return mNameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.mNameCompany = nameCompany;
    }
}
