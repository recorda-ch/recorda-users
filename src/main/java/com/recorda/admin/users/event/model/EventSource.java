package com.recorda.admin.users.event.model;

/**
 * An event source (REST, SOAP, ...)
 */
public enum EventSource {

    /** An event published by a REST API */
    REST,

    /** An event published by a SOAP Web-Service */
    SOAP,

    /** An event published by a JMX component */
    JMX,

    /** All others sources */
    OTHER;

}
