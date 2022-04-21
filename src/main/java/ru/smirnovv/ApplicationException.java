package ru.smirnovv;

/**
 * Ошибка приложения
 */
public class ApplicationException extends Exception {

    /**
     * Конструктор объекта класса
     *
     * @param message сообщение об ошибке
     * @param cause   причина ошибки
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

}
