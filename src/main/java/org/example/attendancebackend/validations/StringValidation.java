package org.example.attendancebackend.validations;

public class StringValidation {
    public static boolean isInvalid(String value){
        return value == null || value.trim().isEmpty() || validateWhiteSpaces(value);
    }

    public static boolean validateWhiteSpaces(String validationString) {
        return validationString.chars().allMatch(Character::isWhitespace);
    }
}
