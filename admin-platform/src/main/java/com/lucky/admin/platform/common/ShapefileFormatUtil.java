package com.lucky.admin.platform.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;

public class ShapefileFormatUtil {

	public static String shp2Json(String shpPath){
		
		StringBuffer sb = new StringBuffer();
		FeatureJSON fjson = new FeatureJSON();
		try{
			sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
			File file = new File(shpPath);
			ShapefileDataStore shpDataStore = null;
			shpDataStore = new ShapefileDataStore(file.toURL());
			//设置编码
			Charset charset = Charset.forName("UTF-8");
			shpDataStore.setCharset(charset);
			String typeName = shpDataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = null;
			featureSource =  shpDataStore.getFeatureSource (typeName);
			SimpleFeatureCollection result = featureSource.getFeatures();
			SimpleFeatureIterator itertor = result.features();
			JSONArray array = new JSONArray();
			while (itertor.hasNext())
			{
				SimpleFeature feature = itertor.next();
				StringWriter writer = new StringWriter();
				fjson.writeFeature(feature, writer);
				array.add(writer);

			}
			itertor.close();
			sb.append(array.toString());
			sb.append("}");
			//写到文件
			//writeToFile("C:\\GISFile\\testFile.geojson",sb.toString());
		}
		catch(Exception e){
			 e.printStackTrace();

		}
		return sb.toString();	
	}
	/**
     * 写出到文件
     * @param targetFile  目标文件路径
     * @param soure         json
     */
	public static void writeToFile(String targetFile,String soure) {
		File file = new File(targetFile);
		try {
			OutputStream os = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(soure);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
