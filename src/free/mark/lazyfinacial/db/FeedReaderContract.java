package free.mark.lazyfinacial.db;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class AssetEntry implements BaseColumns {
        public static final String TABLE_NAME = "asset";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_REMARKS = "remarks";
        public static final String COLUMN_NAME_DATE = "update_date";
    }
}