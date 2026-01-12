package com.probase.potzr.SmartBanking.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.text.WordUtils;

import java.util.stream.Stream;

public enum CoreBankingType {
    FLEXCUBE("FLEX"),
    FINACLE("FINACLE"),
    OPENAPI("OPENAPI");

    private String abbreviation;

    CoreBankingType(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return this.abbreviation;
    }

    private static Object[] expected = Stream.of(CoreBankingType.values()).map(CoreBankingType::value).toArray();

    @JsonCreator
    public static CoreBankingType from(String value) throws Exception {
        for (CoreBankingType c : CoreBankingType.values()) {
            if (c.value().equalsIgnoreCase(value)
                    || c.name().equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new Exception("CoreBankingType.class, value, expected");
    }

    @JsonValue
    public String value() {
        return WordUtils.capitalizeFully(this.name().replaceAll("_", " "));
    }

    public static Object[] expected() {return expected;}
}
