package com.recorda.admin.users.filter;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.client.ApiClient;
import com.recorda.admin.users.i18n.MessageResolver;
import com.recorda.admin.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Implements a filter on user location
 */
@Component
public class LocationFilter implements GenericFilter<User> {

    @Value("${api.ipapi.uri}")
    private String apiEndpoint;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private MessageResolver messageResolver;

    @Override
    public boolean filter(User user) throws BusinessException, TechnicalException {

        // Based on IP, forge URI for "IP Location API" country endpoint
        String finalEndpoint = apiEndpoint.concat(String.format("/%s/country",user.getIp()));
        UriComponents uri = UriComponentsBuilder.fromUriString(finalEndpoint).build();

        // Invoke
        String country = (String) apiClient.get(uri.toUri(), /* no header requested*/ null, String.class);

        // Filter on "CH" (Switzerland)
        return "CH".equals(country.toUpperCase());
    }
}
