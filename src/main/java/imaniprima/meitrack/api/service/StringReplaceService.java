package imaniprima.meitrack.api.service;

import org.hibernate.boot.model.naming.Identifier;
import org.springframework.stereotype.Service;

@Service
public class StringReplaceService {

    public String strReplace(String originalString, String oldChar, String newChar){
        String newValue = originalString.replace(oldChar,newChar);
        return newValue;
    }

    public String pointToCommaReplace(String originalString){
        String newValue = originalString.replace('.',',');
        return newValue;
    }

    public Identifier convertToSnakeCase(String item) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = item
                .replaceAll(regex, replacement)
                .toLowerCase();
        return Identifier.toIdentifier(newName);
    }

    public String convertCamelCaseToSpace(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

}
