package free.mark.lazyfinacial.db;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.test.InstrumentationTestCase;
import android.util.Log;
import free.mark.lazyfinacial.FullscreenActivity;
import free.mark.lazyfinacial.beans.Asset;
import free.mark.lazyfinacial.db.FeedReaderDbHelper;
 
public class FeedReaderDbHelperTest extends InstrumentationTestCase {
    private FullscreenActivity fullActivity = null;
    private FeedReaderDbHelper dbHelper = null;
    
    /*
     * 初始设置
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()  {
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("free.mark.lazyfinacial", FullscreenActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fullActivity = (FullscreenActivity) getInstrumentation().startActivitySync(intent);
        dbHelper = new FeedReaderDbHelper(fullActivity.getBaseContext());
    }
 
    /*
     * 垃圾清理与资源回收
     * @see android.test.InstrumentationTestCase#tearDown()
     */
    @Override
    protected void tearDown()  {
    	fullActivity.finish();
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void testCreateDb() throws Exception {
        Log.v("testActivity", "test the Activity");
        dbHelper.onCreate(dbHelper.getWritableDatabase());
        assertEquals("Eall", "Eall");
    }
    
    public void testQueryAll() throws Exception {
    	List<Asset> assetList = dbHelper.queryAll();
    	for(Asset asset : assetList) {
    		System.out.println("=======Test Item : " + asset.getItem());
    	}
    }
    
    public void testInsert() throws Exception {
    	Asset asset = new Asset();
    	asset.setAmount(15.5);
    	asset.setItem("aaaa");
    	asset.setRemarks("bbbb");
    	asset.setDate(new Date());
    	dbHelper.insert(asset);
    	testQueryAll();
    }
}
