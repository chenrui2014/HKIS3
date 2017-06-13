package com.huake.hkis.hkis;

import com.huake.hkis.hkis.IScanResult;
interface IScan{

	int init();  //init scaner engine
	
	void close();
	
	void scan();  //start scanning 
	
	void setOnResultListener(IScanResult iLister); //listen scan result
	
	void setChar(String charSetName);
}