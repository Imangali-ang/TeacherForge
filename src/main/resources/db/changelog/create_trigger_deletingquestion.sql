CREATE OR REPLACE FUNCTION correct_question_number()
RETURNS TRIGGER AS
$$
BEGIN
UPDATE questions
SET number = number - 1
WHERE number > OLD.number AND test_id = OLD.test_id;
RETURN OLD;
END;
$$
LANGUAGE plpgsql;


CREATE TRIGGER update_question_number
    AFTER DELETE
    ON questions
    FOR EACH ROW
    EXECUTE FUNCTION correct_question_number();