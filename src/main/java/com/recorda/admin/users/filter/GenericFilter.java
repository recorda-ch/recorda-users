package com.recorda.admin.users.filter;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.TechnicalException;

/**
 * A generic filter
 *
 * @param <T> what kind of class to filter
 */
public interface GenericFilter<T> {

    public boolean filter(T what) throws BusinessException, TechnicalException;
}
