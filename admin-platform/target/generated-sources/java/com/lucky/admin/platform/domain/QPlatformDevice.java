package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlatformDevice is a Querydsl query type for PlatformDevice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformDevice extends EntityPathBase<PlatformDevice> {

    private static final long serialVersionUID = -311412668L;

    public static final QPlatformDevice platformDevice = new QPlatformDevice("platformDevice");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath alarmRules = createString("alarmRules");

    public final NumberPath<Long> belongRegion = createNumber("belongRegion", Long.class);

    public final DateTimePath<java.sql.Timestamp> buyDate = createDateTime("buyDate", java.sql.Timestamp.class);

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final StringPath description = createString("description");

    public final StringPath enableDate = createString("enableDate");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath installAddress = createString("installAddress");

    public final DateTimePath<java.sql.Timestamp> installDate = createDateTime("installDate", java.sql.Timestamp.class);

    public final StringPath installLocation = createString("installLocation");

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final StringPath manuFacturer = createString("manuFacturer");

    public final StringPath name = createString("name");

    public final StringPath standard = createString("standard");

    public final StringPath stats = createString("stats");

    public final StringPath supplier = createString("supplier");

    public final StringPath type = createString("type");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QPlatformDevice(String variable) {
        super(PlatformDevice.class, forVariable(variable));
    }

    public QPlatformDevice(Path<? extends PlatformDevice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlatformDevice(PathMetadata metadata) {
        super(PlatformDevice.class, metadata);
    }

}

