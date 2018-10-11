-- EE-9410 Migrate all 'whynot' entries in detail column of feedback table to be 'reasonForNotMatch'

-- For each row containing 'whynot' as a json key in detail
-- Get the value of 'whynot'
-- Add "reasonForNotMatch": "whynotvalue" to json
-- Remove 'whynot' from json

UPDATE feedback
SET detail = (detail || ('{"reasonForNotMatch": "' || (detail->>'whynot') || '"}')::jsonb)  - 'whynot'
WHERE detail ?? 'whynot';