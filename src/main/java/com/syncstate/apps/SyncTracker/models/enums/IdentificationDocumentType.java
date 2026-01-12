package com.probase.potzr.SmartBanking.models.enums;

public enum IdentificationDocumentType {
    PASSPORT("Passport"),
    DRIVERS_LICENCE("Drivers Licence");

    public final String value;

    private IdentificationDocumentType(String value) {
        this.value = value;
    }

    public static IdentificationDocumentType valueOfLabel(String label) {
        for (IdentificationDocumentType e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
