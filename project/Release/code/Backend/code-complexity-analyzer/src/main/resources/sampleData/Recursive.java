 class FibonacciMain {
    public static long fibonacci(long number) {
        if ((number == 0) || (number == 1)) { // base cases
            return number;
        } else if{
            // recursion step
            return fibonacci(number - 1) + fibonacci(number - 2);
        }else {
            // recursion step
            return fibonacci(number - 1) + fibonacci(number - 2);
        }
        for(count = 0; count < prefix; ++count){
            netArr[count] = resultArr[count];
            for(count = 0; count < prefix; ++count){
                netArr[count] = resultArr[count];
                for(count = 0; count < prefix; ++count){
                    netArr[count] = resultArr[count];
                    for(count = 0; count < prefix; ++count){
                        netArr[count] = resultArr[count];
                        for(count = 0; count < prefix; ++count){
                            netArr[count] = resultArr[count];
                        }
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
        for (int count = 0; count <= 10; count++) {
            System.out.println("Fibonacci of " + count + " is " + fibonacci(count));
        }
    }
}