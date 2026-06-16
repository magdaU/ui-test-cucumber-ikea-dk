package com.ikea.uitestcucumberikeadk.utils;

import java.time.Duration;

/**
 * Central place for test configuration: base URL and timeouts.
 */
public final class Config {

    public static final String BASE_URL = "https://www.ikea.com/dk/da/";

    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final Duration PRODUCT_PAGE_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration SEARCH_TIMEOUT = Duration.ofSeconds(20);
    public static final Duration COOKIE_BANNER_TIMEOUT = Duration.ofSeconds(5);

    private Config() {
    }
}
