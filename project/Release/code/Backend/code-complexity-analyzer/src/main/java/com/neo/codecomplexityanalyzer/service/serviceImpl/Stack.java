package com.neo.codecomplexityanalyzer.service.serviceImpl;

public class Stack {
    private String arr[];
    private int top;
    private int capacity;

    // Constructor to initialize stack
    Stack(int size) {
        arr = new String[size];
        capacity = size;
        top = -1;
    }

    // Utility function to add an element x in the stack
    public void push(String x) {
        if (isFull()) {
            System.out.println("OverFlow\nProgram Terminated\n");
            System.exit(1);
        }
        arr[++top] = x;
    }

    // Utility function to pop top element from the stack
    public String pop() {
        // check for stack underflow
        if (isEmpty()) {
            System.out.println("UnderFlow\nProgram Terminated");
            System.exit(1);
        }
        // decrease stack size by 1 and (optionally) return the popped element
        return arr[top--];
    }

    // Utility function to return top element in a stack
    public String peek() {
        if (!isEmpty())
            return arr[top];
        else
            System.exit(1);

        return "error";
    }

    // Utility function to return the size of the stack
    public int size() {
        return top + 1;
    }

    // Utility function to check if the stack is empty or not
    public Boolean isEmpty() {
        return top == -1; // or return size() == 0;
    }

    // Utility function to check if the stack is full or not
    public Boolean isFull() {
        return top == capacity - 1; // or return size() == capacity;
    }
}