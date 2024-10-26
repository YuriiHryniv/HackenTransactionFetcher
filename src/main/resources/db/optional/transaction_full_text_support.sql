-- Add the search_vector column
ALTER TABLE transaction
    ADD COLUMN search_vector tsvector;

-- Create a function to update the search_vector field
CREATE OR REPLACE FUNCTION update_transaction_search_vector()
RETURNS TRIGGER AS $$
BEGIN
    NEW.search_vector := to_tsvector('english',
        coalesce(NEW.block_hash, '') || ' ' ||
        coalesce(NEW.value, '') || ' ' ||
        coalesce(NEW.type, '') || ' ' ||
        coalesce(NEW.hash, '') || ' ' ||
        coalesce(NEW.input, '')
    );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger to update the search_vector on INSERT or UPDATE
CREATE TRIGGER trigger_update_transaction_search_vector
    BEFORE INSERT OR UPDATE ON transaction
                         FOR EACH ROW
                         EXECUTE FUNCTION update_transaction_search_vector();

-- Create a GIN index on the search_vector column for efficient full-text searching
CREATE INDEX idx_transaction_search_vector
    ON transaction
    USING GIN (search_vector);