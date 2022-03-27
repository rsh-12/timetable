BEGIN;

ALTER TABLE teacher
    DROP CONSTRAINT teacher_phone_check;

ALTER TABLE teacher
    ADD CONSTRAINT teacher_phone_check CHECK ( phone ~ '\+?\d([\s-]?\d{3}){0,2}([\s-]?\d{2}){2}' );
COMMIT;