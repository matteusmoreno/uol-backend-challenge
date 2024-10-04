package br.com.matteusmoreno.uol_backend_challenge.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JusticeLeague {
    @JacksonXmlElementWrapper(localName = "codinomes")
    @JacksonXmlProperty(localName = "codinome")
    private List<String> codinomes;
}
