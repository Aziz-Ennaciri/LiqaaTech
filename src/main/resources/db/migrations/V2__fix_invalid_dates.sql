-- Update any invalid dates to a valid future date
UPDATE events 
SET date_time = DATE_ADD(NOW(), INTERVAL 1 DAY)
WHERE date_time = '0000-00-00 00:00:00'
   OR date_time IS NULL
   OR date_time < NOW();

-- Add a check constraint to prevent future invalid dates
ALTER TABLE events 
ADD CONSTRAINT valid_date_time CHECK (date_time >= NOW());
