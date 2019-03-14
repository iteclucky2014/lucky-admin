package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlatformCheckPoint is a Querydsl query type for PlatformCheckPoint
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformCheckPoint extends EntityPathBase<PlatformCheckPoint> {

    private static final long serialVersionUID = -2144348074L;

    public static final QPlatformCheckPoint platformCheckPoint = new QPlatformCheckPoint("platformCheckPoint");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final NumberPath<Long> belongRegion = createNumber("belongRegion", Long.class);

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final StringPath description = createString("description");

    public final ComparablePath<Character> deviceType = createComparable("deviceType", Character.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final StringPath stats = createString("stats");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QPlatformCheckPoint(String variable) {
        super(PlatformCheckPoint.class, forVariable(variable));
    }

    public QPlatformCheckPoint(Path<? extends PlatformCheckPoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlatformCheckPoint(PathMetadata metadata) {
        super(PlatformCheckPoint.class, metadata);
    }

}

