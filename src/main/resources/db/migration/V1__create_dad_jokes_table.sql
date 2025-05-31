-- Create the dad_jokes table
CREATE TABLE IF NOT EXISTS dad_jokes (
    id BIGSERIAL PRIMARY KEY,
    joke_text TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Add some initial jokes
INSERT INTO dad_jokes (joke_text) VALUES 
    ('Why don''t skeletons fight each other? They don''t have the guts.'),
    ('What do you call cheese that isn''t yours? Nacho cheese.'),
    ('Why couldn''t the bicycle stand up by itself? It was two tired.'),
    ('What do you call fake spaghetti? An impasta.'),
    ('I''m reading a book about anti-gravity. It''s impossible to put down!'),
    ('Did you hear about the mathematician who''s afraid of negative numbers? He''ll stop at nothing to avoid them.'),
    ('Why don''t scientists trust atoms? Because they make up everything!'),
    ('I told my wife she was drawing her eyebrows too high. She looked surprised.'),
    ('What do you call a bear with no teeth? A gummy bear.'),
    ('I''m on a seafood diet. Every time I see food, I eat it.');
