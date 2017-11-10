package com.sen5.lib.api.encrypt;

import android.util.Log;


public class DID {
	
	/**
	 * 从key里获取DID
	 * @param key
	 * @return
	 */
	public static String getDID(String key) {
		
		String strDID = "";
		if(null != key) {
			try {
				StringBuilder sbKey = new StringBuilder(Dec3Decode.decodeDidKey(key));
				sbKey.insert(5, "-");
				sbKey.insert(12, "-");
				strDID = sbKey.substring(0, 18);
			} catch (Exception e) {
				if(true) {
					Log.e("DID", "error == " + e.getMessage());					
				}
			}
		}
		return strDID;		
	}

	////
}
