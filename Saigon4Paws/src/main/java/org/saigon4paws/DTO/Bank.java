package org.saigon4paws.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class Bank {
    private int id;

    private String name;

    private String shortName;

    private String code;

    private String bin;
}
