package game;

class FIO {
    private final String firstName;
    private final String secondName;

    public FIO(String _firstName, String _secondName){
        firstName = _firstName;
        secondName = _secondName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getSecondName(){
        return secondName;
    }

    @Override
    public String toString() {
        if(firstName.equals(""))
            return secondName;
        if(secondName.equals(""))
            return firstName;
        return firstName + " " + secondName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FIO && ((FIO) obj).getFirstName().equals(firstName)
                && ((FIO) obj).getSecondName().equals(secondName);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}