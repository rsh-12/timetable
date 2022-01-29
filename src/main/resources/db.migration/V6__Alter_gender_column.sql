ALTER TABLE teacher
    ALTER COLUMN gender SET DEFAULT 'FEMALE',
    ADD CHECK ( gender IN ('FEMALE', 'female', 'MALE', 'male'));