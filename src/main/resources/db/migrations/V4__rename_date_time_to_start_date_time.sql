-- Rename the column from date_time to start_date_time
ALTER TABLE events 
    CHANGE COLUMN date_time start_date_time DATETIME NOT NULL;

-- First drop the existing start_date_time column if it exists
ALTER TABLE events 
    DROP COLUMN IF EXISTS start_date_time;

-- Then rename the date_time column to start_date_time
ALTER TABLE events 
    CHANGE COLUMN date_time start_date_time DATETIME NOT NULL;

-- Update existing records to have valid dates
UPDATE events 
SET start_date_time = CURRENT_TIMESTAMP 
WHERE start_date_time IS NULL OR start_date_time < CURRENT_TIMESTAMP;
