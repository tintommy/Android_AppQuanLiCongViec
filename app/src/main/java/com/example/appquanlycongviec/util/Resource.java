package com.example.appquanlycongviec.util;

public abstract class Resource<T> {
    private final T data;
    private final String message;

    private Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading(T data) {
            super(data, null);
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(String message, T data) {
            super(data, message);
        }
    }

    public static class Unspecified<T> extends Resource<T> {
        public Unspecified(T data) {
            super(data, null);
        }
    }
}
