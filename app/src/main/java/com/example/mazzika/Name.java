package com.example.mazzika;

public class Name {
    private String fName, mName, lName;

    public Name(String fName, String mName, String lName) {
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public String getmName() {
        return mName;
    }

    public String getlName() {
        return lName;
    }

    @Override
    public String toString() {
        return fName + " " + lName;
    }
}
