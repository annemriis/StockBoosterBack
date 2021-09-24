CREATE SCHEMA IF NOT EXISTS schema;
CREATE TABLE IF NOT EXISTS schema.users (
                                                 id INT2,

                                                 name TEXT NOT NULL,
                                                 email TEXT NOT NULL,
                                                 password TEXT NOT NULL,
                                                 stocks  TEXT[] not null,
                                                 PRIMARY KEY (id)
);

