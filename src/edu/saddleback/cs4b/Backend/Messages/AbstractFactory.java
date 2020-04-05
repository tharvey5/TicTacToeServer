package edu.saddleback.cs4b.Backend.Messages;

public interface AbstractFactory<T> {
    T createMessage(String type);
}
