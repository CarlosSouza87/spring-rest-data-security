-- sample.organization test data
INSERT INTO organizations (id, `name`, institution_name, street, city, state, country, zipcode)
VALUES (NEXT VALUE FOR organizations_seq, 'organization 1', 'institution 1', 'some street', 'some city', 'some state', 'some country', '123456');
INSERT INTO organizations (id, `name`, institution_name, street, city, state, country, zipcode)
VALUES (NEXT VALUE FOR organizations_seq, 'organization 2', 'institution 2', 'another street', 'another city', 'another state', 'another country', '654321');