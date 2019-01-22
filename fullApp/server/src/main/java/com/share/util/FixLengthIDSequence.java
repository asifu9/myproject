package com.share.util;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang.StringUtils;

import com.share.domain.SequenceGen;

/**
 * This is a customized Sequence Generator which simulates Oracle DB Sequence Generator. However the master sequence
 * is stored locally in the file as there is no access to Oracle database. The output format is "prefix" + number.
 * <p>
 * <u><b>Sample output:</u></b><br>
 * 1. FixLengthIDSequence(null,null,15,0,99,0) will generate 15, 16, ... 99, 00<br>
 * 2. FixLengthIDSequence(null,"K",1,1,99,0) will generate K01, K02, ... K99, K01<br>
 * 3. FixLengthIDSequence(null,"SG",100,2,9999,100) will generate SG0100, SG0101, ... SG8057, (in case server crashes, the new init value will start from last cache value+1) SG8101, ... SG9999, SG0002<br>
 */
public final class FixLengthIDSequence {

    private  String unqiueName;
    private  String PREFIX;
    private  AtomicLong SEQ_ID;
    private  long MINVALUE;
    private  long MAXVALUE;
    private  long CACHEVALUE;

    // some internal working values.
    private int iMaxLength; // max numeric length excluding prefix, for left padding zeros.
    private long lNextSnapshot; // to keep track of when to update sequence value to file. 
    public  boolean bInit = false; // to enable ShutdownHook routine after program has properly initialized



    public String getUnqiueName() {
		return unqiueName;
	}

	public void setUnqiueName(String unqiueName) {
		this.unqiueName = unqiueName;
	}

	/**
     * This POJO style constructor should be initialized via Spring Singleton. Otherwise, rewrite this constructor into Singleton design pattern.
     * 
     * @param sFilename This is the absolute file path to store the sequence number. To reset the sequence, this file needs to be removed manually.
     * @param prefix The hard-coded identifier.
     * @param initvalue
     * @param minvalue
     * @param maxvalue
     * @param cache
     * @throws Exception
     */
    public FixLengthIDSequence(String sFilename, String prefix, long initvalue, long minvalue, long maxvalue, int cache) throws Exception {
        bInit = false;
        unqiueName =sFilename;
        PREFIX = (prefix==null)?"":prefix;
        SEQ_ID = new AtomicLong(initvalue);
        MINVALUE = minvalue;
        MAXVALUE = maxvalue; 
        iMaxLength = Long.toString(MAXVALUE).length();
        CACHEVALUE = (cache <= 0)?1:cache; 
        lNextSnapshot = initvalue+1; // Internal cache is always 1, equals no cache.

        
        SEQ_ID = new AtomicLong(lNextSnapshot+1); // SEQ_ID starts fresh from lNextSnapshot+!.
      //  lNextSnapshot = roundUpNumberByMultipleValue(SEQ_ID.longValue(),CACHEVALUE);
    

        // Catch invalid values.
        if (minvalue < 0) {
            throw new Exception("MINVALUE cannot be less than 0");
        }
        if (maxvalue < 0) {
            throw new Exception("MAXVALUE cannot be less than 0");
        }
        if (minvalue >= maxvalue) {
            throw new Exception("MINVALUE cannot be greater than MAXVALUE");
        }
        if (cache >= maxvalue) {
            throw new Exception("CACHE value cannot be greater than MAXVALUE");
        }

        // Save the next Snapshot.
       // saveToLocal(lNextSnapshot);
        bInit = true;
    }
    
    public FixLengthIDSequence(SequenceGen gen) throws Exception {
        bInit = false;

        // If sequence file exists and valid, restore the saved sequence.

            PREFIX = gen.getName();
            SEQ_ID = new AtomicLong(gen.getValue()); // savedInitValue
            MINVALUE =gen.getMINVALUE();
            MAXVALUE = gen.getMAXVALUE(); 
            iMaxLength = String.valueOf(gen.getMAXVALUE()).length();
            CACHEVALUE = gen.getCACHEVALUE();
            lNextSnapshot = gen.getlNextSnapshot();
            System.out.println("value is here " + gen.getValue());
            // For sequence number recovery
            // The rule to determine to continue using SEQ_ID or lNextSnapshot as subsequent sequence number:
            // If savedInitValue = savedSnapshot, it was saved by ShutdownHook -> use SEQ_ID.
            // Else if saveInitValue < savedSnapshot, it was saved by periodic Snapshot -> use lNextSnapshot+1.

            SEQ_ID = new AtomicLong(lNextSnapshot+1); // SEQ_ID starts fresh from lNextSnapshot+!.
           // lNextSnapshot = roundUpNumberByMultipleValue(SEQ_ID.longValue(),CACHEVALUE);
        


        // Save the next Snapshot.
        //saveToLocal(lNextSnapshot);
        bInit = true;
    }

    /**
     * Equivalent to Oracle Sequence nextval.
     * @return String because Next Value is usually left padded with zeros, e.g. "00001".
     */
    public synchronized String nextVal() {
        if (SEQ_ID.longValue() > MAXVALUE) {
            SEQ_ID.set(MINVALUE);
            lNextSnapshot = MINVALUE+Long.valueOf("1");
        }

        if (SEQ_ID.longValue() > lNextSnapshot) {
            lNextSnapshot = lNextSnapshot+Long.valueOf("1");
            //saveToLocal(lNextSnapshot);
        }
        System.out.println(" imaxxlength " + iMaxLength);
        return PREFIX.concat(StringUtils.leftPad(Long.toString(SEQ_ID.getAndIncrement()),iMaxLength,"0"));
    }

    /**
     * Store sequence value into the local file. This routine is called either by Snapshot or ShutdownHook routines.<br>
     * If called by Snapshot, currentCount == Snapshot.<br>
     * If called by ShutdownHook, currentCount == current SEQ_ID.
     * @param currentCount - This value is inserted by either Snapshot or ShutdownHook routines.
     */


    /**
     * Load the sequence file content into String.
     * @return
     */
    private String loadToString() {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(unqiueName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

  
	public String getPREFIX() {
		return PREFIX;
	}

	public void setPREFIX(String pREFIX) {
		PREFIX = pREFIX;
	}

	public AtomicLong getSEQ_ID() {
		return SEQ_ID;
	}

	public void setSEQ_ID(AtomicLong sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}

	public long getMINVALUE() {
		return MINVALUE;
	}

	public void setMINVALUE(long mINVALUE) {
		MINVALUE = mINVALUE;
	}

	public long getMAXVALUE() {
		return MAXVALUE;
	}

	public void setMAXVALUE(long mAXVALUE) {
		MAXVALUE = mAXVALUE;
	}

	public long getCACHEVALUE() {
		return CACHEVALUE;
	}

	public void setCACHEVALUE(long cACHEVALUE) {
		CACHEVALUE = cACHEVALUE;
	}



	public long getlNextSnapshot() {
		return lNextSnapshot;
	}

	public void setlNextSnapshot(long lNextSnapshot) {
		this.lNextSnapshot = lNextSnapshot;
	}

    /**
     * Main method for testing purpose.
     * @param args
     */
//    public static void main(String[] args) throws Exception {
//        //FixLengthIDSequence(Filename, prefix, initvalue, minvalue, maxvalue, cache)
//        FixLengthIDSequence seq = new FixLengthIDSequence("USER","USER",50,1,999999999,10);
//        for (int i=0; i<12; i++) {
//            System.out.println(seq.nextVal());
//            Thread.sleep(1000);
//            //if (i==8) { System.exit(0); }
//        }
//    }

}