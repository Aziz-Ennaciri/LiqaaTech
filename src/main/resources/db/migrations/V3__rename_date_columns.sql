-- Rename date_time column to start_date_time
ALTER TABLE events 
CHANGE date_time start_date_time DATETIME NOT NULL;

-- Add end_date_time column if it doesn't exist
ALTER TABLE events 
ADD COLUMN IF NOT EXISTS end_date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Update any invalid dates to valid future dates
UPDATE events 
SET start_date_time = DATE_ADD(NOW(), INTERVAL 1 DAY),
    end_date_time = DATE_ADD(NOW(), INTERVAL 2 DAY)
WHERE start_date_time = '0000-00-00 00:00:00'
   OR start_date_time IS NULL
   OR start_date_time < NOW();

-- Add check constraints to prevent future invalid dates
ALTER TABLE events 
ADD CONSTRAINT valid_start_date_time CHECK (start_date_time >= NOW()),
ADD CONSTRAINT valid_end_date_time CHECK (end_date_time >= NOW());
