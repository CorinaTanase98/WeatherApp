package ro.mta.se.lab.model;

public class DummyCity extends City{

    public DummyCity() {
        super(null,null,0,null,null);
    }

    public DummyCity(String name) {
        super(null);
    }

    @Override
    public String getName() {
        throw new RuntimeException("Dummy City");
    }

    @Override
    public int getID() {
        throw new RuntimeException("Dummy City");
    }
    @Override
    public String getCountryCode(){
        throw new RuntimeException("Dummy City");
    }
}
