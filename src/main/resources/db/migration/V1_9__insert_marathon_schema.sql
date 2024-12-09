-- sample.marathon test data
INSERT INTO marathons(id, `name`, weight, score)
VALUES (NEXT VALUE FOR marathons_seq, 'marathon 1', '1', '1');
INSERT INTO marathons(id, `name`, weight, score)
VALUES (NEXT VALUE FOR marathons_seq, 'marathon 2', '2', '2');