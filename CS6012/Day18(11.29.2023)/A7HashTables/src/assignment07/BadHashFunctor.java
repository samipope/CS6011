package assignment07;

public class BadHashFunctor implements HashFunctor{

  // private int collisions = 0;

    @Override
    public int hash(String item) {
        int toReturn = 1;
        for (int i = 0; i < item.length(); i++) {
            toReturn++;
        }

        return  toReturn;
    }
}
