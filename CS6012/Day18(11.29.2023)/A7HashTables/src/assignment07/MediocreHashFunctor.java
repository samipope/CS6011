package assignment07;


public class MediocreHashFunctor implements HashFunctor {
    @Override
    public int hash(String item) {
        int l = item.length();
        long sum = 0;
        for (int i = 0; i < l; i++) {
            sum += item.charAt(i);
        }
        return (int) sum;
    }
}
