
public class NestedIf {

	private static int time;

    public void printMessage() {
        if (time < 10) {
            System.out.println("Good outer.");
            if (time < 8) {
                System.out.println("Good 2nd level.");
                if (time < 5) {
                    System.out.println("Good 3rd level.");
                    if (time < 10) {
                        System.out.println("Good outer.");
                        if (time < 8) {
                            System.out.println("Good 2nd level.");
                            if (time < 5) {
                                System.out.println("Good 3rd level.");
                                if (time < 10) {
                                    System.out.println("Good outer.");
                                    if (time < 8) {
                                        System.out.println("Good 2nd level.");
                                        if (time < 5) {
                                            System.out.println("Good 3rd level.");
                                            if (time < 10) {
                                                System.out.println("Good outer.");
                                                if (time < 8) {
                                                    System.out.println("Good 2nd level.");
                                                    if (time < 5) {
                                                        System.out.println("Good 3rd level.");
                                                        if (time < 10) {
                                                            System.out.println("Good outer.");
                                                            if (time < 8) {
                                                                System.out.println("Good 2nd level.");
                                                                if (time < 5) {
                                                                    System.out.println("Good 3rd level.");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
