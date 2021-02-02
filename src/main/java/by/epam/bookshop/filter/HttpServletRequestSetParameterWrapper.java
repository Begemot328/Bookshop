package by.epam.bookshop.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

    public class HttpServletRequestSetParameterWrapper extends HttpServletRequestWrapper {
    private final Map<String, String[]> myParameters = new HashMap<>();

    public HttpServletRequestSetParameterWrapper(HttpServletRequest request) {
        super(request);
        this.myParameters.putAll(request.getParameterMap());
    }

    public void setParameter(String parameterName, String parameter) {
        myParameters.put(parameterName, new String[]{parameter});
    }

    @Override
    public String getParameter(String parameterName) {
        if (myParameters.get(parameterName) == null
                || myParameters.get(parameterName).length == 0) {
            return null;
        } else {
            return myParameters.get(parameterName)[0];
        }
    }

    @Override
    public String[] getParameterValues(String parameterName) {
        if (myParameters.get(parameterName) == null) {
            return null;
        } else {
            return myParameters.get(parameterName);
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.myParameters;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return  Collections.enumeration(this.myParameters.keySet());
    }

}
