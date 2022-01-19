ALTER TABLE planned_timetable
    ADD FOREIGN KEY (day_id) REFERENCES day (id) ON DELETE CASCADE,
    ADD FOREIGN KEY (period_id) REFERENCES period (id) ON DELETE CASCADE,
    ADD FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE,
    ADD FOREIGN KEY (group_id) REFERENCES student_group (id) ON DELETE CASCADE,
    ADD FOREIGN KEY (audience_id) REFERENCES audience (id) ON DELETE CASCADE