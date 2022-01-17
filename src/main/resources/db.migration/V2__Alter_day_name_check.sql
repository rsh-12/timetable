ALTER TABLE day
    DROP CONSTRAINT day_name_check,
    ADD CONSTRAINT day_name_check
        CHECK ( name IN ('monday', 'MONDAY',
                         'tuesday', 'TUESDAY',
                         'wednesday', 'WEDNESDAY',
                         'thursday', 'THURSDAY',
                         'friday', 'FRIDAY',
                         'saturday', 'SATURDAY',
                         'sunday', 'SUNDAY'));