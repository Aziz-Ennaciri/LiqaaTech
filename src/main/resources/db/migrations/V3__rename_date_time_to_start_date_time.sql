-- Rename date_time column to start_date_time
ALTER TABLE events 
CHANGE date_time start_date_time DATETIME NOT NULL;

-- Update any invalid dates to a valid future date
UPDATE events 
SET start_date_time = DATE_ADD(NOW(), INTERVAL 1 DAY)
WHERE start_date_time = '0000-00-00 00:00:00'
   OR start_date_time IS NULL
   OR start_date_time < NOW();

-- Add a check constraint to prevent future invalid dates
ALTER TABLE events 
ADD CONSTRAINT valid_start_date_time CHECK (start_date_time >= NOW());
