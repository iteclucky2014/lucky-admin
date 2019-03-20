package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.ApiResultCode;
import com.lucky.admin.platform.domain.PlatformCheckPoint;
import com.lucky.admin.platform.domain.PlatformDept;
import com.lucky.admin.platform.domain.QPlatformCheckPoint;
import com.lucky.admin.platform.domain.QPlatformDept;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "homePage", tags = "主页")
@RestController
@RequestMapping(value = "homePage")
@SuppressWarnings("unchecked")
public class PlatformHomePageController {   
	@Autowired
	JPAQueryFactory queryFactory;
	
    // 全部评价
	@ApiOperation(value = "INDEX", notes = "INDEX")
	@GetMapping(value = "getIndex/{deptId}")
    public ApiResult<Map<String, Object>> getIndex(@PathVariable Long deptId) {

        Map<String, Object> indexInfo = new HashMap<>();
        QPlatformDept qPlatformDept = QPlatformDept.platformDept;
        PlatformDept target = null; 
        if(deptId != null && deptId != 0)
        {
        	target = queryFactory.selectFrom(qPlatformDept).where(qPlatformDept.id.eq(deptId)).fetchOne();
        }
        else
        {
        	target = queryFactory.selectFrom(qPlatformDept).where(qPlatformDept.deptId.eq("jn")).fetchOne();
        }
                
        //显示地图中心位置
        String[] centerPoint = target.getCenterPoint().split(",");
        Double[] centerData = {Double.parseDouble(centerPoint[0]),Double.parseDouble(centerPoint[1])};
        int zoom = target.getZoom();
          
        QPlatformCheckPoint qPlatformCheckPoint = QPlatformCheckPoint.platformCheckPoint;
               
        //流量计报警点
        BooleanExpression whereExp = qPlatformCheckPoint.deviceType.eq('1');
        //报警的隐患或者故障
        whereExp = whereExp.and(qPlatformCheckPoint.stats.notEqualsIgnoreCase("1"));
        if(deptId != null && deptId != 0)
        {
        	whereExp = whereExp.and(qPlatformCheckPoint.belongRegion.eq(deptId));
        }
        
        List<PlatformCheckPoint> platformCheckPoint = queryFactory
        		.selectFrom(qPlatformCheckPoint)
        		.where(whereExp)
        		.fetch();
        List<Double[]> markerData = new ArrayList<Double[]>();
        for(int i=0;i<platformCheckPoint.size();i++)
        {
        	PlatformCheckPoint checkPoint = platformCheckPoint.get(i);
        	String[] locationPoint = checkPoint.getLocation().split(",");
            Double[] location = {Double.parseDouble(locationPoint[0]),Double.parseDouble(locationPoint[1])};
            markerData.add(location);
        } 
        
        indexInfo.put("centerData", centerData);
        indexInfo.put("markerData", markerData);
        indexInfo.put("zoom",zoom);
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("查询成功").data(indexInfo).build();
		
	}
		
    // 得到文件
	@ApiOperation(value = "getShpFileInfo", notes = "getShpFileInfo")
	@GetMapping(value = "getShpFileInfo")
	public String getShpFileInfo() {		
		 //读取收文件转正json
        String shpPath = System.getProperty("user.dir") + "\\shapefile\\津南区.shp";
        System.out.println(shpPath);
        String json = "";        
		return json;
	}

}
