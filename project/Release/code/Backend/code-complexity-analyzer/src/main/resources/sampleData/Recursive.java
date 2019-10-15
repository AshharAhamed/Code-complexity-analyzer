class FibonacciMain {
	public static long fibonacci(long number) {
		if ((number == 0) || (number == 1)) { 
			return number;
		} else if{
			return fibonacci(number - 1) + fibonacci(number - 2);
		}else {
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
		System.out.println("Ashhar");
		System.out.println("Ashhar");
	}

	public static void main(String args[]) {
		for (int count = 0; count <= 10; count++) {
			System.out.println("Fibonacci of " + count + " is " + fibonacci(count));
		}
	}
}