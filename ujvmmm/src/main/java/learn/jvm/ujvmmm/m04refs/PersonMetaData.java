package learn.jvm.ujvmmm.m04refs;

import java.util.Date;

class PersonMetaData {
    private final Date date;

    public PersonMetaData() {
        date = new Date();
    }

    @Override
    public String toString() {
        return "PersonMetaData{date=" + date + '}';
    }
}
