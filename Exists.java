package com.williams.kailyn;

import java.sql.Statement;

public interface Exists {
    boolean exists(Statement statement, String table, String attribute, String value);
}
