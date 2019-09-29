class FibonacciMain {
	public static long fibonacci(long number) {
		int count, prefix, netArr;
		if ((number == 0) || (number == 1)) { 
			return number;
		} else if(number == 3){
			return fibonacci(number - 1) + fibonacci(number - 2);
		}else {
			return fibonacci(number - 1) + fibonacci(number - 2);
		}
	}

	public static void main(String args[]) {
		for (int count = 0; count <= 10; count++) {
			System.out.println("Fibonacci of " + count + " is " + fibonacci(count));
		}
	}
}