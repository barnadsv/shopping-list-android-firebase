package lab.com.br.shoppinglist.infrastructure;

import java.util.HashMap;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class ServiceResponse {

    private HashMap<String, String> propertyErrors;

    public ServiceResponse() {
        propertyErrors = new HashMap<>();
    }

    public void setPropertyErrors(String property, String error) {
        propertyErrors.put(property, error);
    }

    public String getPropertyError(String property) {
        return propertyErrors.get(property);
    }

    public boolean didSucceed() {
        return (propertyErrors.size() == 0);
    }
}
