package com.dezzzl.exceptions;

public class PersonNotFoundException extends Exception{
    /**
     * Конструктор, создающий Exception, если пользователь не найден
     * @param message Сообщение об ошибке
     */
    public PersonNotFoundException(String message){
        super(message);
    }
}
