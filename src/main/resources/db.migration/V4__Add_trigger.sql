BEGIN TRANSACTION;

DROP TRIGGER IF EXISTS unique_default_subsription_trigger ON subscription;

CREATE OR REPLACE FUNCTION set_is_default_field_to_true()
    RETURNS TRIGGER AS
$body$
BEGIN
    IF new.is_default = TRUE THEN
        UPDATE subscription
        SET is_default = FALSE
        WHERE id != new.id
          AND user_id = new.user_id;
    END IF;

    RETURN new;
END;
$body$
    LANGUAGE plpgsql;

CREATE TRIGGER unique_default_subsription_trigger
    AFTER INSERT OR UPDATE
    ON subscription
    FOR EACH ROW
EXECUTE PROCEDURE set_is_default_field_to_true();

COMMIT;

