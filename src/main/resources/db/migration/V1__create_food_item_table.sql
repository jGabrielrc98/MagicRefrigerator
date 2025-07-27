CREATE TABLE food_item (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           categoria VARCHAR(255),
                           quantidade INT,
                           validade TIMESTAMP
);

