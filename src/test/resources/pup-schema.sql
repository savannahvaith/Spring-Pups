drop table if exists owner CASCADE;
drop table if exists puppy CASCADE;
CREATE TABLE owner (
    id BIGINT AUTO_INCREMENT,
    age INTEGER NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE puppy (
    id BIGINT AUTO_INCREMENT,
    age INTEGER NOT NULL,
    breed VARCHAR(255),
    name VARCHAR(255),
    skill BOOLEAN NOT NULL,
    owner_id BIGINT,
    PRIMARY KEY (id)
);

