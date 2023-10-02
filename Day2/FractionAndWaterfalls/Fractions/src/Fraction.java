public class Fraction {
    private long numerator;
    private long denominator;

    //----private methods-------
    private long GCD(long numerator, long denominator){
        long gcd = numerator;
        long remainder = denominator;
        while (remainder != 0) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }

    //reduce by the greatest common denominator
    //kept this function private
        private void reduce(Fraction fraction){
            long newGCD = GCD(this.numerator, this.denominator);
            if(newGCD!=0){
                this.numerator /= newGCD;
                this.denominator /= newGCD;
            }
        }


    // default constructor - don't have to check for denom=0
    public Fraction() {
        if (denominator==0){
            throw new IllegalArgumentException("Denominator cannot be 0");
        }
        try {
            if (numerator < 0 && denominator < 0) {
                numerator = -1 * numerator;
                denominator = -1 * denominator;
            }
            this.numerator = 0;
            this.denominator = 1;
        }catch (IndexOutOfBoundsException e){
            System.out.println("Exception Message");
        }
    }
    // constructor where you pass in numerator and denominator
    public Fraction(long numerator, long denominator) {
        if (denominator==0){
            throw new IllegalArgumentException("Denominator cannot be 0");
        }
        try {
            if (numerator < 0 && denominator < 0) {
                numerator = -1 * numerator;
                denominator = -1 * denominator;
            }
            this.numerator = numerator;
            this.denominator = denominator;
            //call in reduce here?
            reduce(this);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Exception Message");}
    }

    public Fraction plus(Fraction rhs){
            long newDenominator = this.denominator * rhs.denominator;
            long newNumerator = (this.numerator * rhs.denominator) + (rhs.numerator * this.denominator);
            Fraction resultofPlus = new Fraction(newNumerator, newDenominator);
            return resultofPlus;
        }
    public Fraction minus(Fraction rhs){
        reduce(rhs);
            long newDenominator = this.denominator * rhs.denominator;
            long newNumerator = (this.numerator * rhs.denominator) - (rhs.numerator * this.denominator);
        Fraction resultofMinus = new Fraction(newNumerator, newDenominator);
        return resultofMinus;

    }

    public Fraction times(Fraction rhs){
        long newNumerator= rhs.numerator * this.numerator;
        long newDenominator= rhs.denominator * this.denominator;
        Fraction resultofTimes = new Fraction(newNumerator, newDenominator);
        return resultofTimes;
    }

    public Fraction dividedBy(Fraction rhs){
        long newNumerator=this.numerator * rhs.denominator;
        long newDenominator=this.denominator * rhs.numerator;
        Fraction resultofDivided = new Fraction(newNumerator, newDenominator);
        return resultofDivided;
    }
    public Fraction reciprocal(){
        long newDenom = this.denominator;
        long newNumer = this.numerator;
        this.numerator=newDenom;
        this.denominator=newNumer;
        return this;
    }

    //overloading the tostring function that already exists
    public String toString(){
        reduce(this);
        return numerator + "/" + denominator;
    }

    //overloading the todouble function that already exists
    public double toDouble(){
     double fractionDouble = numerator/denominator;
     return  fractionDouble;
    }

}