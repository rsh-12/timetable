BEGIN;

ALTER TABLE teacher
    DROP CONSTRAINT teacher_phone_check;

ALTER TABLE teacher
    ADD CONSTRAINT teacher_phone_check CHECK ( phone ~ '^(\+?\d)[ 0-9\-()]{6,19}' );

COMMIT;