package com.dezzzl.exceptions;

public class EdgeNotFoundException extends Exception{

        /**
         * Конструктор, создающий Exception, если ребро не найдено
         * @param message Сообщение об ошибке
         */
        public EdgeNotFoundException(String message){
            super(message);
        }
}
