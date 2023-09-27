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
        if(numerator<0 && denominator<0){
            numerator = -1* numerator;
            denominator = -1*denominator;
        }
        this.numerator = 0;
        this.denominator = 1;
    }
    // constructor where you pass in numerator and denominator
    public Fraction(long numerator, long denominator) {
        if(numerator<0 && denominator<0){
            numerator = -1* numerator;
            denominator = -1*denominator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
        //call in reduce here?
    }

    public void plus(Fraction rhs){
            long newDenominator = this.denominator * rhs.denominator;
            long newNumerator = (this.numerator * rhs.denominator) + (rhs.numerator * this.denominator);
            this.numerator = newNumerator;
            this.denominator = newDenominator;
            reduce(this);
        }
    public void minus(Fraction rhs){
        reduce(rhs);
            long newDenominator = this.denominator * rhs.denominator;
            long newNumerator = (this.numerator * rhs.denominator) - (rhs.numerator * this.denominator);

            this.numerator = newNumerator;
            this.denominator = newDenominator;
    }

    public void times(Fraction rhs){
        this.numerator= rhs.numerator * this.numerator;
        this.denominator= rhs.denominator * this.denominator;
    }

    public void dividedBy(Fraction rhs){
        this.numerator=this.numerator * rhs.denominator;
        this.denominator=this.denominator * rhs.numerator;
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