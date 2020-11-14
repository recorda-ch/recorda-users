package com.recorda.admin.users.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * A generic i18n message resolver.
 *
 * The requested locale is provided in YAML file.
 */
@Component
public class MessageResolver {

    @Value("${i18n.locale}")
    private String locale;

    @Autowired
    private MessageSource messageSource;

    public String getContent(String idMessage, Object[] params) {
        return messageSource.getMessage(idMessage, params, new Locale(locale));
    }

}
